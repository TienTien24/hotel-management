package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.ServiceStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.Invoice;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.BookingServiceUsageRepository;
import com.hotel.management.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private BookingService bookingService;

    public double calculateRoomCharges(Booking booking) {
        if (booking.getRoom() == null || booking.getRoom().getPrice() == null) {
            return 0;
        }
        if (booking.getCheckInDate() == null || booking.getCheckOutDate() == null) {
            return booking.getRoom().getPrice();
        }
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if (nights <= 0) {
            nights = 1;
        }
        return booking.getRoom().getPrice() * nights;
    }

    public double calculateCompletedServiceCharges(Long bookingId) {
        List<BookingServiceUsage> usages = usageRepository.findByBookingId(bookingId);
        return usages.stream()
                .filter(u -> u.getStatus() == ServiceStatus.COMPLETED)
                .mapToDouble(u -> u.getService().getPrice() * u.getQuantity())
                .sum();
    }

    /**
     * Tính lại hóa đơn: tiền phòng + dịch vụ đã hoàn thành, cập nhật booking.totalPrice.
     * Nếu khách đã trả tiền nhưng phát sinh thêm dịch vụ → chuyển lại UNPAID.
     */
    public Invoice recalculateAndSyncInvoice(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        double roomCharges = calculateRoomCharges(booking);
        double serviceCharges = calculateCompletedServiceCharges(bookingId);
        double totalAmount = roomCharges + serviceCharges;

        Invoice invoice = invoiceRepository.findByBookingId(bookingId).orElse(new Invoice());
        double previousTotal = invoice.getTotalAmount() != null ? invoice.getTotalAmount() : 0;

        invoice.setBooking(booking);
        invoice.setRoomCharges(roomCharges);
        invoice.setServiceCharges(serviceCharges);
        invoice.setTotalAmount(totalAmount);

        booking.setTotalPrice(totalAmount);

        PaymentStatus paymentStatus = booking.getPaymentStatus() == null
                ? PaymentStatus.UNPAID
                : booking.getPaymentStatus();

        if (paymentStatus == PaymentStatus.PAID
                && previousTotal > 0
                && totalAmount > previousTotal + 0.001) {
            paymentStatus = PaymentStatus.UNPAID;
            booking.setPaymentStatus(PaymentStatus.UNPAID);
            invoice.setPaymentDate(null);
        }

        invoice.setPaymentStatus(paymentStatus);
        if (paymentStatus == PaymentStatus.PAID && invoice.getPaymentDate() == null) {
            invoice.setPaymentDate(LocalDateTime.now());
        }

        bookingRepository.save(booking);
        return invoiceRepository.save(invoice);
    }

    public Invoice generateInvoice(Long bookingId) {
        return recalculateAndSyncInvoice(bookingId);
    }

    public Invoice syncInvoiceForBooking(Long bookingId) {
        return recalculateAndSyncInvoice(bookingId);
    }

    /** Thu tiền theo hóa đơn hiện tại (sau khi đã tính lại). */
    public Invoice settlePayment(Long bookingId) {
        Invoice invoice = recalculateAndSyncInvoice(bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Không thể thanh toán cho booking đã bị hủy");
        }

        booking.setPaymentStatus(PaymentStatus.PAID);
        bookingRepository.save(booking);

        invoice.setPaymentStatus(PaymentStatus.PAID);
        invoice.setPaymentDate(LocalDateTime.now());
        return invoiceRepository.save(invoice);
    }

    public Invoice checkOut(Long bookingId) {
        bookingService.checkOut(bookingId);
        return getInvoiceByBookingId(bookingId);
    }

    public Invoice getInvoiceByBookingId(Long bookingId) {
        return invoiceRepository.findByBookingId(bookingId)
                .orElseGet(() -> recalculateAndSyncInvoice(bookingId));
    }
}
