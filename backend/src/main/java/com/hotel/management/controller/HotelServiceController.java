package com.hotel.management.controller;

import com.hotel.management.enums.ServiceStatus;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.HotelService;
import com.hotel.management.service.HotelServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-services")
@CrossOrigin(origins = "*")
public class HotelServiceController {

    @Autowired
    private HotelServiceService hotelServiceService;

    @GetMapping
    public List<HotelService> getAllServices() {
        return hotelServiceService.getAllServices();
    }

    @GetMapping("/usages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public List<BookingServiceUsage> getAllUsages() {
        return hotelServiceService.getAllUsages();
    }

    @PostMapping("/add-to-booking")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<BookingServiceUsage> addServiceToBooking(
            @RequestParam Long bookingId,
            @RequestParam Long serviceId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String note) {
        return ResponseEntity.ok(hotelServiceService.addServiceToBooking(bookingId, serviceId, quantity, note));
    }

    @PutMapping("/usages/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<BookingServiceUsage> updateUsageStatus(
            @PathVariable Long id,
            @RequestParam ServiceStatus status) {
        return ResponseEntity.ok(hotelServiceService.updateUsageStatus(id, status));
    }

    @GetMapping("/booking/{bookingId}")
    public List<BookingServiceUsage> getUsagesByBookingId(@PathVariable Long bookingId) {
        return hotelServiceService.getUsagesByBookingId(bookingId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<HotelService> createService(@RequestBody HotelService hotelService) {
        return ResponseEntity.ok(hotelServiceService.createService(hotelService));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<HotelService> updateService(@PathVariable Long id, @RequestBody HotelService hotelService) {
        return ResponseEntity.ok(hotelServiceService.updateService(id, hotelService));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        hotelServiceService.deleteService(id);
        return ResponseEntity.ok().build();
    }
}
