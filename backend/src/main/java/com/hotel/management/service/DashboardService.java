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

    @Autowired
    private RoomService roomService;

    public Map<String, Object> getStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            // getAllRooms() tự động đồng bộ trạng thái phòng dựa trên booking hiện tại
            List<Room> allRooms = roomService.getAllRooms();
            
            long totalRooms = allRooms.size();
            long occupiedRooms = 0;
            long cleaningRooms = 0;
            long maintenanceRooms = 0;
            long availableRooms = 0;
            long bookedRooms = 0;

            for (Room r : allRooms) {
                String statusStr = String.valueOf(r.getStatus());
                if ("OCCUPIED".equals(statusStr)) occupiedRooms++;
                else if ("CLEANING".equals(statusStr)) cleaningRooms++;
                else if ("MAINTENANCE".equals(statusStr)) maintenanceRooms++;
                else if ("BOOKED".equals(statusStr)) bookedRooms++;
                else if ("AVAILABLE".equals(statusStr)) availableRooms++;
            }

            double occupancyRate = totalRooms > 0 ? (double) occupiedRooms / totalRooms * 100 : 0;
            
            double totalRevenue = 0;
            try {
                totalRevenue = invoiceRepository.findAll().stream()
                        .filter(i -> i.getPaymentStatus() == PaymentStatus.PAID)
                        .mapToDouble(i -> i.getTotalAmount() != null ? i.getTotalAmount() : 0.0)
                        .sum();
            } catch (Exception e) {
                System.err.println("Lỗi tính doanh thu: " + e.getMessage());
            }

            LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            long newBookings = 0;
            try {
                newBookings = bookingRepository.findAll().stream()
                        .filter(b -> b.getCreatedAt() != null)
                        .filter(b -> !b.getCreatedAt().isBefore(todayStart) && !b.getCreatedAt().isAfter(todayEnd))
                        .count();
            } catch (Exception e) {
                System.err.println("Lỗi tính booking mới: " + e.getMessage());
            }

            stats.put("totalRooms", totalRooms);
            stats.put("occupiedRooms", occupiedRooms);
            stats.put("cleaningRooms", cleaningRooms);
            stats.put("maintenanceRooms", maintenanceRooms);
            stats.put("availableRooms", availableRooms);
            stats.put("bookedRooms", bookedRooms);
            stats.put("occupancyRate", occupancyRate);
            stats.put("totalRevenue", totalRevenue);
            stats.put("newBookings", newBookings);
            
            System.out.println("Stats calculated: " + stats);

            return stats;
        } catch (Exception e) {
            System.err.println("Lỗi nghiêm trọng trong getStats: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
