package com.hotel.management.repository;

import com.hotel.management.model.BookingServiceUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingServiceUsageRepository extends JpaRepository<BookingServiceUsage, Long> {
    List<BookingServiceUsage> findByBookingId(Long bookingId);
}
