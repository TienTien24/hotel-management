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
            
            Double totalRevenue = 0.0;
            try {
                // Tối ưu: Sử dụng query trực tiếp để tính tổng doanh thu
                totalRevenue = invoiceRepository.sumTotalRevenue();
                if (totalRevenue == null) totalRevenue = 0.0;
                
                // Cộng thêm doanh thu từ các booking đang hoạt động nhưng chưa có hóa đơn (nếu cần)
                // Tuy nhiên, báo cáo thường dựa trên hóa đơn đã thanh toán nên sumTotalRevenue là đủ.
                
                System.out.println("DEBUG Dashboard: totalRevenue = " + totalRevenue);
            } catch (Exception e) {
                System.err.println("Lỗi tính doanh thu: " + e.getMessage());
            }

            // Đếm số lượng booking mới trong ngày (Tối ưu bằng query)
            LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            long newBookings = 0;
            try {
                newBookings = bookingRepository.countByCreatedAtBetween(todayStart, todayEnd);
                
                if (newBookings == 0) {
                    newBookings = bookingRepository.countActiveBookings();
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

        // Tối ưu: Lấy hóa đơn có booking liên kết bằng query thay vì findAll()
        Map<Long, Invoice> invoicesByBookingId = invoiceRepository.findAllWithBooking().stream()
                .collect(Collectors.toMap(i -> i.getBooking().getId(), i -> i, (a, b) -> a));

        // Tối ưu: Sử dụng repository method để lọc booking theo ngày
        List<Booking> bookingsInRange = bookingRepository.findBookingsInDateRange(startDateTime, endDateTime);

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
