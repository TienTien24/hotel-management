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
    public ResponseEntity<Invoice> generateInvoice(@PathVariable Long bookingId) {
        return ResponseEntity.ok(invoiceService.generateInvoice(bookingId));
    }

    @PutMapping("/booking/{bookingId}/check-out")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Invoice> checkOut(@PathVariable Long bookingId) {
        return ResponseEntity.ok(invoiceService.checkOut(bookingId));
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('CUSTOMER')")
    public ResponseEntity<Invoice> getInvoiceByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(invoiceService.getInvoiceByBookingId(bookingId));
    }
}
