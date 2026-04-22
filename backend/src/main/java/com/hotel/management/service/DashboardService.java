package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Invoice;
import com.hotel.management.model.Room;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.InvoiceRepository;
import com.hotel.management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                .filter(i -> i.getPaymentStatus() == com.hotel.management.enums.PaymentStatus.PAID)
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
}
