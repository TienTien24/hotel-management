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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
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
        booking.setPaymentStatus(resolveInitialPaymentStatus(booking.getPaymentMethod()));
        booking.setStatus(booking.getPaymentMethod() == PaymentMethod.COD ? BookingStatus.PENDING : BookingStatus.CONFIRMED);
        booking.setTotalPrice(room.getPrice() * nights);

        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking checkIn(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));
        booking.setStatus(BookingStatus.CHECKED_IN);
        booking.setCheckedInAt(LocalDateTime.now());

        Room room = booking.getRoom();
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);

        return bookingRepository.save(booking);
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
        booking.setStatus(BookingStatus.COMPLETED); // Đánh dấu là đã hoàn thành để hiện lên blog
        
        // Chọn một phòng ngẫu nhiên để gắn vào blog nếu cần, hoặc để null
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            booking.setRoom(rooms.get((int) (Math.random() * rooms.size())));
        }

        return bookingRepository.save(booking);
    }

    private PaymentStatus resolveInitialPaymentStatus(PaymentMethod paymentMethod) {
        if (paymentMethod == PaymentMethod.COD) {
            return PaymentStatus.UNPAID;
        }
        return PaymentStatus.PAID;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
