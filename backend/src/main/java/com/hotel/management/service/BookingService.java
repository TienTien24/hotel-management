package com.hotel.management.service;

import com.hotel.management.dto.BookingRequest;
import com.hotel.management.dto.ReviewRequest;
import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentMethod;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.Room;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.User;
import com.hotel.management.repository.*;
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

    @Autowired
    private BookingServiceUsageRepository bookingServiceUsageRepository;

    @Autowired
    private RoomService roomService;

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

        Booking savedBooking = bookingRepository.save(booking);
        
        // Cập nhật trạng thái phòng ngay khi có booking mới
        roomService.syncRoomStatus(room);
        
        return savedBooking;
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
        roomService.syncRoomStatus(room);
        
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
        roomService.syncRoomStatus(room);
        
        bookingRepository.save(booking);
    }

    public Booking checkIn(Long bookingId, String guestIdNumber, String guestIdImageUrl) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + bookingId));
        
        if (booking.getStatus() == BookingStatus.CHECKED_IN) {
            return booking;
        }

        if (booking.getStatus() != BookingStatus.CONFIRMED && booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking #" + bookingId + " không ở trạng thái Chờ xác nhận hoặc Đã xác nhận (Trạng thái hiện tại: " + booking.getStatus() + "). Không thể thực hiện check-in.");
        }
        
        Room room = booking.getRoom();

        if (room.getStatus() == RoomStatus.MAINTENANCE) {
            throw new RuntimeException("Phòng " + room.getRoomNumber() + " đang trong quá trình bảo trì. Không thể thực hiện check-in.");
        }

        if (room.getStatus() == RoomStatus.CLEANING) {
            throw new RuntimeException("Phòng " + room.getRoomNumber() + " đang được dọn dẹp. Vui lòng đợi.");
        }

        // Kiểm tra xem có ai đang ở phòng này không
        if (room.getStatus() == RoomStatus.OCCUPIED) {
            List<Booking> currentOccupants = bookingRepository.findByRoomIdAndStatusIn(room.getId(), List.of(BookingStatus.CHECKED_IN));
            if (!currentOccupants.isEmpty()) {
                Booking occupant = currentOccupants.get(0);
                throw new RuntimeException("Phòng " + room.getRoomNumber() + " đang có khách (#" + occupant.getId() + ": " + occupant.getGuestFullName() + ") đang lưu trú. Vui lòng thực hiện Check-out cho khách cũ trước.");
            }
        }

        booking.setStatus(BookingStatus.CHECKED_IN);
        booking.setCheckedInAt(LocalDateTime.now());
        booking.setGuestIdNumber(guestIdNumber);
        booking.setGuestIdImageUrl(guestIdImageUrl);
        
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);
        
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        // Đồng bộ trạng thái phòng trước khi lấy danh sách booking để đảm bảo dữ liệu room bên trong booking là mới nhất
        roomService.getAllRooms();
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
            roomService.syncRoomStatus(room);
        }

        return savedBooking;
    }

    public Booking checkOut(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + bookingId));

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Booking #" + bookingId + " không ở trạng thái Đã check-in. Không thể thực hiện check-out.");
        }

        // Tính toán lại tổng tiền bao gồm dịch vụ
        List<BookingServiceUsage> usages = bookingServiceUsageRepository.findByBookingId(bookingId);
        double serviceTotal = usages.stream()
                .filter(u -> u.getStatus() == com.hotel.management.enums.ServiceStatus.COMPLETED)
                .mapToDouble(u -> u.getService().getPrice() * u.getQuantity())
                .sum();
        
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if (nights <= 0) nights = 1; // Tối thiểu 1 đêm
        double roomTotal = booking.getRoom().getPrice() * nights;
        
        booking.setTotalPrice(roomTotal + serviceTotal);
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setCheckedOutAt(LocalDateTime.now());

        Room room = booking.getRoom();
        room.setStatus(RoomStatus.CLEANING);
        roomRepository.save(room);

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long bookingId, BookingRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getStatus() == BookingStatus.COMPLETED || booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Không thể cập nhật booking đã hoàn thành hoặc đã hủy");
        }

        if (request.getCheckInDate() != null) booking.setCheckInDate(request.getCheckInDate());
        if (request.getCheckOutDate() != null) booking.setCheckOutDate(request.getCheckOutDate());
        if (request.getGuestFullName() != null) booking.setGuestFullName(request.getGuestFullName());
        if (request.getGuestPhone() != null) booking.setGuestPhone(request.getGuestPhone());
        if (request.getGuestEmail() != null) booking.setGuestEmail(request.getGuestEmail());

        // Recalculate price if dates changed
        if (request.getCheckInDate() != null || request.getCheckOutDate() != null) {
            long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
            booking.setTotalPrice(booking.getRoom().getPrice() * nights);
        }

        return bookingRepository.save(booking);
    }

    public Booking changeRoom(Long bookingId, Long newRoomId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getStatus() == BookingStatus.COMPLETED || booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Không thể đổi phòng cho booking đã hoàn thành hoặc đã hủy");
        }

        Room oldRoom = booking.getRoom();
        Room newRoom = roomRepository.findById(newRoomId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng mới"));

        if (newRoom.getStatus() != RoomStatus.AVAILABLE) {
            throw new RuntimeException("Phòng mới không khả dụng");
        }

        // Check for overlap in new room
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                newRoomId,
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );
        // Exclude current booking from overlap check
        overlapping.removeIf(b -> b.getId().equals(bookingId));

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Phòng mới đã được đặt trong khoảng thời gian này");
        }

        booking.setRoom(newRoom);
        // Update price
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setTotalPrice(newRoom.getPrice() * nights);

        Booking savedBooking = bookingRepository.save(booking);

        // Update room statuses
        roomService.syncRoomStatus(oldRoom);
        roomService.syncRoomStatus(newRoom);

        return savedBooking;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
