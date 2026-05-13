package com.hotel.management.repository;

import com.hotel.management.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByBookingId(Long bookingId);

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.paymentStatus = 'PAID'")
    Double sumTotalRevenue();

    @Query("SELECT i FROM Invoice i WHERE i.booking IS NOT NULL")
    List<Invoice> findAllWithBooking();
}
