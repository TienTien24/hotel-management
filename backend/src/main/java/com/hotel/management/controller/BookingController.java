package com.hotel.management.controller;

import com.hotel.management.dto.BookingRequest;
import com.hotel.management.dto.ReviewRequest;
import com.hotel.management.model.Booking;
import com.hotel.management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            return ResponseEntity.ok(bookingService.createBooking(request));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", t.getMessage()));
        }
    }

    @PutMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.confirmBooking(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", t.getMessage()));
        }
    }

    @PutMapping("/{id}/check-in")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> checkIn(@PathVariable Long id) {
        try {
            System.out.println("Đang thực hiện check-in cho booking ID: " + id);
            Booking booking = bookingService.checkIn(id);
            return ResponseEntity.ok(booking);
        } catch (Throwable t) {
            t.printStackTrace();
            String message = t.getMessage() != null ? t.getMessage() : t.getClass().getSimpleName();
            System.err.println("Lỗi check-in: " + message);
            return ResponseEntity.badRequest().body(java.util.Map.of("message", message));
        }
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id, @RequestParam Long userId, @RequestParam boolean isAdmin) {
        try {
            return ResponseEntity.ok(bookingService.cancelBooking(id, userId, isAdmin));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", t.getMessage()));
        }
    }

    @PostMapping("/{id}/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Booking> submitReview(@PathVariable Long id,
                                                @RequestParam Long customerId,
                                                @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(bookingService.submitReview(id, customerId, request));
    }

    @PostMapping("/quick-review")
    public ResponseEntity<Booking> submitQuickReview(@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(bookingService.submitQuickReview(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/customer/{id}")
    public List<Booking> getCustomerBookings(@PathVariable Long id) {
        return bookingService.getCustomerBookings(id);
    }

    @GetMapping("/completed-checkins")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public List<Booking> getCompletedCheckIns() {
        return bookingService.getCompletedCheckIns();
    }

    @GetMapping("/reviews")
    public List<Booking> getAllReviews() {
        return bookingService.getAllReviews();
    }
}
