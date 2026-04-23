package com.hotel.management.service;

import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking processVnpayCallback(Long bookingId, String transactionRef, String responseCode) {
        if ("00".equals(responseCode)) {
            return updatePaymentStatus(bookingId, PaymentStatus.PAID);
        }
        return bookingRepository.findById(bookingId).orElse(null);
    }

    public Booking processMomoCallback(Long bookingId, String transactionRef, String resultCode) {
        if ("0".equals(resultCode)) {
            return updatePaymentStatus(bookingId, PaymentStatus.PAID);
        }
        return bookingRepository.findById(bookingId).orElse(null);
    }

    private Booking updatePaymentStatus(Long bookingId, PaymentStatus status) {
        return bookingRepository.findById(bookingId).map(booking -> {
            booking.setPaymentStatus(status);
            return bookingRepository.save(booking);
        }).orElse(null);
    }

    public boolean isPaymentCompleted(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(booking -> booking.getPaymentStatus() == PaymentStatus.PAID)
                .orElse(false);
    }
}