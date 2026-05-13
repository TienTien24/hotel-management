package com.hotel.management.repository;

import com.hotel.management.model.Booking;
import com.hotel.management.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<Booking> findAllByOrderByCreatedAtDesc();
    List<Booking> findByStatusOrderByCheckedOutAtDesc(BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.status != 'CANCELLED' " +
           "AND ((b.checkInDate <= :endDate AND b.checkOutDate >= :startDate))")
    List<Booking> findOverlappingBookings(Long roomId, LocalDate startDate, LocalDate endDate);
    
    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByRoomIdAndStatusIn(Long roomId, List<BookingStatus> statuses);

    @Query("SELECT b FROM Booking b WHERE b.reviewRating IS NOT NULL ORDER BY b.reviewCreatedAt DESC")
    List<Booking> findAllReviews();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.createdAt >= :start AND b.createdAt <= :end")
    long countByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status != 'CANCELLED'")
    long countActiveBookings();

    @Query("SELECT b FROM Booking b WHERE " +
           "(b.checkedOutAt >= :start AND b.checkedOutAt <= :end) OR " +
           "(b.checkedOutAt IS NULL AND b.createdAt >= :start AND b.createdAt <= :end)")
    List<Booking> findBookingsInDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end);
}
