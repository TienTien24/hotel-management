package com.hotel.management.controller;

import com.hotel.management.model.Invoice;
import com.hotel.management.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> generateInvoice(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(invoiceService.generateInvoice(bookingId));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", t.getMessage()));
        }
    }

    @PutMapping("/booking/{bookingId}/check-out")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> checkOut(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(invoiceService.checkOut(bookingId));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", t.getMessage()));
        }
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('CUSTOMER')")
    public ResponseEntity<?> getInvoiceByBookingId(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(invoiceService.getInvoiceByBookingId(bookingId));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", t.getMessage()));
        }
    }
}
