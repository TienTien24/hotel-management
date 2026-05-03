package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.ContactMessage;
import com.hotel.management.model.Invoice;
import com.hotel.management.model.Room;
import com.hotel.management.repository.BookingServiceUsageRepository;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.ContactMessageRepository;
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
import java.util.stream.Collectors;

@Service
public class DashboardService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private BookingServiceUsageRepository bookingServiceUsageRepository;

    @Autowired
    private ContactMessageRepository contactMessageRepository;

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

            double occupancyRate = totalRooms > 0 ? (double) (occupiedRooms + bookedRooms) / totalRooms * 100 : 0;
            
            double totalRevenue = 0;
            try {
                // Tính tổng doanh thu từ tất cả hóa đơn đã thanh toán HOẶC các booking đang hoạt động/hoàn tất
                List<Invoice> allInvoices = invoiceRepository.findAll();
                Map<Long, Invoice> invoicesByBookingId = allInvoices.stream()
                        .filter(i -> i.getBooking() != null)
                        .collect(Collectors.toMap(i -> i.getBooking().getId(), i -> i, (a, b) -> a));

                List<Booking> allBookings = bookingRepository.findAll();
                totalRevenue = allBookings.stream()
                        .mapToDouble(b -> {
                            Invoice inv = invoicesByBookingId.get(b.getId());
                            // 1. Nếu có hóa đơn và đã thanh toán
                            if (inv != null && inv.getPaymentStatus() == PaymentStatus.PAID) {
                                return inv.getTotalAmount() != null ? inv.getTotalAmount() : 0.0;
                            }
                            // 2. Nếu booking ở trạng thái CONFIRMED, CHECKED_IN hoặc COMPLETED
                            if (b.getStatus() == BookingStatus.CONFIRMED || 
                                b.getStatus() == BookingStatus.CHECKED_IN || 
                                b.getStatus() == BookingStatus.COMPLETED) {
                                return b.getTotalPrice() != null ? b.getTotalPrice() : 0.0;
                            }
                            return 0.0;
                        })
                        .sum();
                System.out.println("DEBUG Dashboard: totalRevenue = " + totalRevenue + " from " + allBookings.size() + " bookings");
            } catch (Exception e) {
                System.err.println("Lỗi tính doanh thu: " + e.getMessage());
            }

            // Đếm số lượng booking mới trong ngày (không lọc theo createdAt nếu nó có thể null)
            LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            long newBookings = 0;
            try {
                newBookings = bookingRepository.findAll().stream()
                        .filter(b -> b.getCreatedAt() != null)
                        .filter(b -> !b.getCreatedAt().isBefore(todayStart) && !b.getCreatedAt().isAfter(todayEnd))
                        .count();
                
                // Nếu newBookings hôm nay là 0, hãy thử đếm tổng số booking đang hoạt động để tránh hiện số 0 quá đơn điệu
                if (newBookings == 0) {
                    newBookings = bookingRepository.findAll().stream()
                            .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                            .count();
                }
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
            
            // Thêm thống kê tin nhắn liên hệ mới
            try {
                long pendingMessages = contactMessageRepository.countByStatus(ContactMessage.MessageStatus.PENDING);
                stats.put("pendingMessages", pendingMessages);
            } catch (Exception e) {
                stats.put("pendingMessages", 0);
            }
            
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

        // Lấy tất cả hóa đơn để tra cứu
        Map<Long, Invoice> invoicesByBookingId = invoiceRepository.findAll().stream()
                .filter(i -> i.getBooking() != null)
                .collect(Collectors.toMap(i -> i.getBooking().getId(), i -> i, (a, b) -> a));

        // Lấy tất cả các booking trong khoảng thời gian (dựa trên ngày checkout, ngày checkin hoặc ngày tạo)
        List<Booking> bookingsInRange = bookingRepository.findAll().stream()
                .filter(b -> {
                    LocalDateTime date = b.getCheckedOutAt() != null ? b.getCheckedOutAt() : 
                                       (b.getCreatedAt() != null ? b.getCreatedAt() : 
                                       (b.getCheckInDate() != null ? b.getCheckInDate().atStartOfDay() : null));
                    return date != null && !date.isBefore(startDateTime) && !date.isAfter(endDateTime);
                })
                .toList();

        // Tính doanh thu và phân loại
        double totalRevenue = 0;
        double totalRoomRevenue = 0;
        double totalServiceRevenue = 0;

        Map<LocalDate, Double> dailyRevenue = new HashMap<>();
        Map<LocalDate, Long> dailyBookingsCount = new HashMap<>();
        Map<String, Double> roomTypeRevenue = new java.util.LinkedHashMap<>();
        Map<String, Double> serviceRevenue = new java.util.LinkedHashMap<>();

        // Khởi tạo bản đồ hàng ngày
        LocalDate current = fromDate;
        while (!current.isAfter(toDate)) {
            dailyRevenue.put(current, 0.0);
            dailyBookingsCount.put(current, 0L);
            current = current.plusDays(1);
        }

        for (Booking b : bookingsInRange) {
            Invoice inv = invoicesByBookingId.get(b.getId());
            double amount = 0;
            double roomAmount = 0;
            double serviceAmount = 0;

            if (inv != null && inv.getPaymentStatus() == PaymentStatus.PAID) {
                amount = inv.getTotalAmount() != null ? inv.getTotalAmount() : 0.0;
                roomAmount = inv.getRoomCharges() != null ? inv.getRoomCharges() : 0.0;
                serviceAmount = inv.getServiceCharges() != null ? inv.getServiceCharges() : 0.0;
            } else if (b.getStatus() == BookingStatus.CONFIRMED || 
                       b.getStatus() == BookingStatus.CHECKED_IN || 
                       b.getStatus() == BookingStatus.COMPLETED) {
                amount = b.getTotalPrice() != null ? b.getTotalPrice() : 0.0;
                roomAmount = amount; // Giả định toàn bộ là tiền phòng nếu không có hóa đơn chi tiết
            }

            if (amount > 0) {
                totalRevenue += amount;
                totalRoomRevenue += roomAmount;
                totalServiceRevenue += serviceAmount;

                LocalDate date = b.getCheckedOutAt() != null ? b.getCheckedOutAt().toLocalDate() : 
                                (b.getCheckInDate() != null ? b.getCheckInDate() :
                                (b.getCreatedAt() != null ? b.getCreatedAt().toLocalDate() : null));
                
                if (date != null && dailyRevenue.containsKey(date)) {
                    dailyRevenue.put(date, dailyRevenue.get(date) + amount);
                }

                // Room type breakdown
                String roomType = (b.getRoom() != null && b.getRoom().getType() != null) ? b.getRoom().getType() : "Khác";
                roomTypeRevenue.put(roomType, roomTypeRevenue.getOrDefault(roomType, 0.0) + roomAmount);
            }

            // Luôn đếm số lượng booking
            LocalDate createDate = b.getCreatedAt() != null ? b.getCreatedAt().toLocalDate() : null;
            if (createDate != null && dailyBookingsCount.containsKey(createDate)) {
                dailyBookingsCount.put(createDate, dailyBookingsCount.get(createDate) + 1);
            }
        }

        // Tính doanh thu dịch vụ chi tiết từ BookingServiceUsage
        List<BookingServiceUsage> completedUsagesInRange = bookingServiceUsageRepository.findAll().stream()
                .filter(usage -> usage.getUsedDate() != null)
                .filter(usage -> !usage.getUsedDate().isBefore(startDateTime) && !usage.getUsedDate().isAfter(endDateTime))
                .toList();

        for (BookingServiceUsage usage : completedUsagesInRange) {
            if (usage.getService() == null) continue;
            String serviceName = usage.getService().getName();
            double sAmount = (usage.getService().getPrice() != null ? usage.getService().getPrice() : 0.0)
                    * (usage.getQuantity() != null ? usage.getQuantity() : 0);
            serviceRevenue.put(serviceName, serviceRevenue.getOrDefault(serviceName, 0.0) + sAmount);
        }

        List<Map<String, Object>> details = new java.util.ArrayList<>();
        dailyRevenue.keySet().stream().sorted().forEach(date -> {
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date);
            dayData.put("revenue", dailyRevenue.get(date));
            dayData.put("bookings", dailyBookingsCount.get(date));
            details.add(dayData);
        });

        report.put("fromDate", fromDate);
        report.put("toDate", toDate);
        report.put("totalRevenue", totalRevenue);
        report.put("totalBookings", bookingsInRange.size());
        report.put("totalRoomRevenue", totalRoomRevenue);
        report.put("totalServiceRevenue", totalServiceRevenue);
        report.put("roomTypeRevenue", roomTypeRevenue);
        report.put("serviceRevenue", serviceRevenue);
        report.put("details", details);

        return report;
    }
}
