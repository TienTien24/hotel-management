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

    @Query("SELECT b FROM Booking b WHERE b.reviewRating IS NOT NULL ORDER BY b.reviewCreatedAt DESC")
    List<Booking> findAllReviews();
}
