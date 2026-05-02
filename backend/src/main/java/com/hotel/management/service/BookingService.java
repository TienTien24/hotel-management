package com.hotel.management.service;

import com.hotel.management.dto.BookingRequest;
import com.hotel.management.dto.ReviewRequest;
import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentMethod;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.Room;
import com.hotel.management.model.User;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.RoomRepository;
import com.hotel.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingRequest request) {
        if (request.getCheckInDate() == null || request.getCheckOutDate() == null) {
            throw new RuntimeException("Vui lòng chọn ngày nhận và ngày trả phòng");
        }

        if (!request.getCheckInDate().isBefore(request.getCheckOutDate())) {
            throw new RuntimeException("Ngày trả phòng phải sau ngày nhận phòng");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));

        if (request.getNumberOfGuests() == null || request.getNumberOfGuests() <= 0) {
            throw new RuntimeException("Số người phải lớn hơn 0");
        }

        if (room.getCapacity() != null && request.getNumberOfGuests() > room.getCapacity()) {
            throw new RuntimeException("Phòng không đủ sức chứa cho số lượng khách đã chọn");
        }

        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Phòng đã được đặt trong khoảng thời gian này");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        User customer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setGuestFullName(isBlank(request.getGuestFullName()) ? customer.getFullName() : request.getGuestFullName());
        booking.setGuestPhone(isBlank(request.getGuestPhone()) ? customer.getPhone() : request.getGuestPhone());
        booking.setGuestEmail(isBlank(request.getGuestEmail()) ? customer.getEmail() : request.getGuestEmail());
        booking.setGuestAddress(request.getGuestAddress());
        booking.setPaymentMethod(request.getPaymentMethod() == null ? PaymentMethod.COD : request.getPaymentMethod());
        booking.setPaymentStatus(PaymentStatus.UNPAID);
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(room.getPrice() * nights);

        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + bookingId));
        
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking #" + bookingId + " không ở trạng thái Chờ xác nhận (Trạng thái hiện tại: " + booking.getStatus() + ")");
        }
        
        booking.setStatus(BookingStatus.CONFIRMED);
        
        Room room = booking.getRoom();
        // Cập nhật trạng thái phòng thông minh dựa trên tất cả các booking active
        releaseRoomIfNoOtherActiveBookings(room);
        
        return bookingRepository.save(booking);
    }

    public void processPaymentSuccess(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + bookingId));
        
        booking.setPaymentStatus(PaymentStatus.PAID);
        // Thanh toán thành công luôn dẫn đến trạng thái CONFIRMED (trừ khi đã hủy)
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Không thể thanh toán cho booking đã bị hủy");
        }
        
        booking.setStatus(BookingStatus.CONFIRMED);
        
        Room room = booking.getRoom();
        // Cập nhật trạng thái phòng thông minh dựa trên tất cả các booking active
        releaseRoomIfNoOtherActiveBookings(room);
        
        bookingRepository.save(booking);
    }

    public Booking checkIn(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + bookingId));
        
        if (booking.getStatus() == BookingStatus.CHECKED_IN) {
            return booking; // Đã check-in rồi thì không làm gì thêm
        }

        if (booking.getStatus() != BookingStatus.CONFIRMED && booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking #" + bookingId + " không ở trạng thái Chờ xác nhận hoặc Đã xác nhận (Trạng thái hiện tại: " + booking.getStatus() + "). Không thể thực hiện check-in.");
        }
        
        Room room = booking.getRoom();

        if (room.getStatus() == RoomStatus.MAINTENANCE) {
            throw new RuntimeException("Phòng " + room.getRoomNumber() + " đang trong quá trình bảo trì. Không thể thực hiện check-in.");
        }

        // Kiểm tra xem có ai đang ở phòng này không
        if (room.getStatus() == RoomStatus.OCCUPIED) {
            // Tìm xem booking nào đang occupy phòng này
            List<Booking> currentOccupants = bookingRepository.findByRoomIdAndStatusIn(room.getId(), List.of(BookingStatus.CHECKED_IN));
            if (!currentOccupants.isEmpty()) {
                Booking occupant = currentOccupants.get(0);
                throw new RuntimeException("Phòng " + room.getRoomNumber() + " đang có khách (#" + occupant.getId() + ": " + occupant.getGuestFullName() + ") đang lưu trú. Vui lòng thực hiện Check-out cho khách cũ trước.");
            } else {
                // Nếu room status là OCCUPIED nhưng không tìm thấy booking nào đang CHECKED_IN
                // thì tự động sửa lại status room và cho phép check-in
                room.setStatus(RoomStatus.AVAILABLE);
                roomRepository.save(room);
            }
        }
        
        booking.setStatus(BookingStatus.CHECKED_IN);
        booking.setCheckedInAt(LocalDateTime.now());
        
        // Lưu trạng thái booking trước khi cập nhật phòng để đảm bảo query trong releaseRoom nhìn thấy trạng thái mới
        Booking savedBooking = bookingRepository.save(booking);

        // Cập nhật trực tiếp trạng thái phòng thành OCCUPIED khi check-in
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);

        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Booking> getCustomerBookings(Long customerId) {
        return bookingRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public List<Booking> getCompletedCheckIns() {
        return bookingRepository.findByStatusOrderByCheckedOutAtDesc(BookingStatus.COMPLETED);
    }

    public Booking submitReview(Long bookingId, Long customerId, ReviewRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getCustomer() == null || !booking.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Bạn không có quyền đánh giá booking này");
        }

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new RuntimeException("Chỉ có thể đánh giá sau khi đã check-out");
        }

        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Điểm đánh giá phải từ 1 đến 5");
        }

        booking.setReviewTitle(request.getTitle());
        booking.setReviewRating(request.getRating());
        booking.setReviewComment(request.getComment());
        booking.setReviewCreatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllReviews() {
        return bookingRepository.findAllReviews();
    }

    public Booking submitQuickReview(ReviewRequest request) {
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Điểm đánh giá phải từ 1 đến 5");
        }

        Booking booking = new Booking();
        booking.setGuestFullName(isBlank(request.getGuestFullName()) ? "Khách hàng" : request.getGuestFullName());
        booking.setReviewTitle(request.getTitle());
        booking.setReviewRating(request.getRating());
        booking.setReviewComment(request.getComment());
        booking.setReviewCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.COMPLETED);

        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            booking.setRoom(rooms.get((int) (Math.random() * rooms.size())));
        }

        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId, Long userId, boolean isAdmin) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking đã được hủy trước đó");
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Không thể hủy booking đã hoàn thành");
        }

        // Kiểm tra quyền hủy (Admin hoặc chính chủ)
        if (!isAdmin && (booking.getCustomer() == null || !booking.getCustomer().getId().equals(userId))) {
            throw new RuntimeException("Bạn không có quyền hủy booking này");
        }

        // Logic xử lý trạng thái phòng và hoàn tiền
        Room room = booking.getRoom();
        boolean shouldReleaseRoom = false;

        if (isAdmin) {
            // Admin có quyền hủy bất cứ lúc nào
            shouldReleaseRoom = true;
            if (booking.getPaymentStatus() == PaymentStatus.PAID) {
                booking.setPaymentStatus(PaymentStatus.REFUNDED);
            }
        } else {
            // Khách hàng tự hủy
            if (booking.getStatus() == BookingStatus.CHECKED_IN) {
                throw new RuntimeException("Không thể tự hủy booking sau khi đã check-in. Vui lòng liên hệ quầy lễ tân.");
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime checkInDateTime = booking.getCheckInDate().atTime(14, 0); // Giả định giờ check-in là 14:00
            long hoursUntilCheckIn = java.time.Duration.between(now, checkInDateTime).toHours();

            if (hoursUntilCheckIn >= 24) {
                // Hủy trước 24h -> hoàn 100%
                if (booking.getPaymentStatus() == PaymentStatus.PAID) {
                    booking.setPaymentStatus(PaymentStatus.REFUNDED);
                }
            } else if (hoursUntilCheckIn >= 0) {
                // Hủy trong vòng 24h -> phí hủy 50%
                booking.setTotalPrice(booking.getTotalPrice() * 0.5);
                if (booking.getPaymentStatus() == PaymentStatus.PAID) {
                    booking.setPaymentStatus(PaymentStatus.REFUNDED); // Trong thực tế sẽ hoàn lại 50% số tiền đã sửa
                }
            } else {
                throw new RuntimeException("Đã quá thời gian check-in. Không thể tự hủy phòng.");
            }
            shouldReleaseRoom = true;
        }

        // Cập nhật trạng thái booking
        booking.setStatus(BookingStatus.CANCELLED);
        Booking savedBooking = bookingRepository.save(booking);

        // Giải phóng phòng nếu cần
        if (shouldReleaseRoom) {
            releaseRoomIfNoOtherActiveBookings(room);
        }

        return savedBooking;
    }

    public void releaseRoomIfNoOtherActiveBookings(Room room) {
        // Kiểm tra xem còn booking nào khác đang active (CONFIRMED hoặc CHECKED_IN) cho phòng này không
        List<BookingStatus> activeStatuses = List.of(BookingStatus.CONFIRMED, BookingStatus.CHECKED_IN);
        List<Booking> activeBookings = bookingRepository.findByRoomIdAndStatusIn(room.getId(), activeStatuses);

        if (activeBookings.isEmpty()) {
            // Nếu không còn booking nào CONFIRMED hoặc CHECKED_IN, nhưng hãy kiểm tra xem phòng có đang bảo trì không
            if (room.getStatus() != RoomStatus.MAINTENANCE) {
                room.setStatus(RoomStatus.AVAILABLE);
            }
        } else {
            // Nếu vẫn còn booking active, kiểm tra xem có ai đang ở không
            boolean hasCheckedIn = activeBookings.stream()
                    .anyMatch(b -> b.getStatus() == BookingStatus.CHECKED_IN);
            if (hasCheckedIn) {
                room.setStatus(RoomStatus.OCCUPIED);
            } else {
                room.setStatus(RoomStatus.BOOKED);
            }
        }
        roomRepository.save(room);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
