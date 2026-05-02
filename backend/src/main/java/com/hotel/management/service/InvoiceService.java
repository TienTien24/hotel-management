package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.Invoice;
import com.hotel.management.model.Room;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.BookingServiceUsageRepository;
import com.hotel.management.repository.InvoiceRepository;
import com.hotel.management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceUsageRepository usageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingService bookingService;

    public Invoice generateInvoice(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        List<BookingServiceUsage> usages = usageRepository.findByBookingId(bookingId);

        double roomCharges = booking.getTotalPrice();
        double serviceCharges = usages.stream()
                .mapToDouble(u -> u.getService().getPrice() * u.getQuantity())
                .sum();

        Invoice invoice = new Invoice();
        invoice.setBooking(booking);
        invoice.setRoomCharges(roomCharges);
        invoice.setServiceCharges(serviceCharges);
        invoice.setTotalAmount(roomCharges + serviceCharges);
        invoice.setPaymentStatus(booking.getPaymentStatus() == null ? PaymentStatus.UNPAID : booking.getPaymentStatus());
        if (booking.getPaymentStatus() == PaymentStatus.PAID) {
            invoice.setPaymentDate(LocalDateTime.now());
        }

        return invoiceRepository.save(invoice);
    }

    public Invoice checkOut(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));
        
        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Chỉ có thể check-out cho booking đã được check-in");
        }
        
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setCheckedOutAt(LocalDateTime.now());
        booking.setPaymentStatus(PaymentStatus.PAID);

        Room room = booking.getRoom();
        // Lưu trạng thái booking trước khi giải phóng phòng
        bookingRepository.save(booking);
        
        // Cập nhật trạng thái phòng thành CLEANING
        room.setStatus(RoomStatus.CLEANING);
        roomRepository.save(room);

        Invoice invoice = invoiceRepository.findByBookingId(bookingId)
                .orElse(new Invoice());
        
        List<BookingServiceUsage> usages = usageRepository.findByBookingId(bookingId);
        double roomCharges = booking.getTotalPrice();
        double serviceCharges = usages.stream()
                .mapToDouble(u -> u.getService().getPrice() * u.getQuantity())
                .sum();

        invoice.setBooking(booking);
        invoice.setRoomCharges(roomCharges);
        invoice.setServiceCharges(serviceCharges);
        invoice.setTotalAmount(roomCharges + serviceCharges);
        invoice.setPaymentStatus(PaymentStatus.PAID);
        invoice.setPaymentDate(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceByBookingId(Long bookingId) {
        return invoiceRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn cho booking này"));
    }
}
