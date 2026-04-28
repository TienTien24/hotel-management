package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.Invoice;
import com.hotel.management.model.Room;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.InvoiceRepository;
import com.hotel.management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalRooms = roomRepository.count();
        long occupiedRooms = roomRepository.findAll().stream()
                .filter(r -> r.getStatus() == RoomStatus.OCCUPIED)
                .count();

        double occupancyRate = totalRooms > 0 ? (double) occupiedRooms / totalRooms * 100 : 0;

        double totalRevenue = invoiceRepository.findAll().stream()
                .filter(i -> i.getPaymentStatus() == PaymentStatus.PAID)
                .mapToDouble(Invoice::getTotalAmount)
                .sum();

        long newBookings = bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.PENDING || b.getStatus() == BookingStatus.CONFIRMED)
                .count();

        stats.put("totalRooms", totalRooms);
        stats.put("occupiedRooms", occupiedRooms);
        stats.put("occupancyRate", occupancyRate);
        stats.put("totalRevenue", totalRevenue);
        stats.put("newBookings", newBookings);

        return stats;
    }

    public Map<String, Object> getRevenueReport(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> report = new HashMap<>();

        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime = toDate.atTime(LocalTime.MAX);

        List<Invoice> invoices = invoiceRepository.findAll().stream()
                .filter(i -> i.getPaymentDate() != null)
                .filter(i -> !i.getPaymentDate().isBefore(startDateTime) && !i.getPaymentDate().isAfter(endDateTime))
                .filter(i -> i.getPaymentStatus() == PaymentStatus.PAID)
                .toList();

        double totalRevenue = invoices.stream()
                .mapToDouble(Invoice::getTotalAmount)
                .sum();

        // Daily breakdown
        Map<LocalDate, Double> dailyRevenue = new HashMap<>();
        Map<LocalDate, Long> dailyBookings = new HashMap<>();

        // Initialize maps with 0 for all dates in range
        LocalDate current = fromDate;
        while (!current.isAfter(toDate)) {
            dailyRevenue.put(current, 0.0);
            dailyBookings.put(current, 0L);
            current = current.plusDays(1);
        }

        // Fill data from invoices
        for (Invoice invoice : invoices) {
            LocalDate date = invoice.getPaymentDate().toLocalDate();
            dailyRevenue.put(date, dailyRevenue.getOrDefault(date, 0.0) + invoice.getTotalAmount());
        }

        // Fill booking counts
        List<Booking> bookingsInRange = bookingRepository.findAll().stream()
                .filter(b -> b.getCreatedAt() != null)
                .filter(b -> !b.getCreatedAt().isBefore(startDateTime) && !b.getCreatedAt().isAfter(endDateTime))
                .toList();

        for (Booking booking : bookingsInRange) {
            LocalDate date = booking.getCreatedAt().toLocalDate();
            if (dailyBookings.containsKey(date)) {
                dailyBookings.put(date, dailyBookings.get(date) + 1);
            }
        }

        List<Map<String, Object>> details = new java.util.ArrayList<>();
        dailyRevenue.keySet().stream().sorted().forEach(date -> {
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date);
            dayData.put("revenue", dailyRevenue.get(date));
            dayData.put("bookings", dailyBookings.get(date));
            details.add(dayData);
        });

        report.put("fromDate", fromDate);
        report.put("toDate", toDate);
        report.put("totalRevenue", totalRevenue);
        report.put("totalBookings", bookingsInRange.size());
        report.put("invoiceCount", invoices.size());
        report.put("details", details);

        return report;
    }
}
