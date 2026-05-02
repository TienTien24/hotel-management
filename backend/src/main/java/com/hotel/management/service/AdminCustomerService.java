package com.hotel.management.service;

import com.hotel.management.dto.AdminCustomerSummary;
import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.enums.RoleName;
import com.hotel.management.model.Booking;
import com.hotel.management.model.Invoice;
import com.hotel.management.model.User;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.InvoiceRepository;
import com.hotel.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminCustomerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<AdminCustomerSummary> getCustomerSummaries() {
        List<User> customers = userRepository.findByRoleOrderByFullNameAsc(RoleName.CUSTOMER);

        Map<Long, List<Booking>> bookingsByCustomer = bookingRepository.findAll().stream()
                .filter(booking -> booking.getCustomer() != null && booking.getCustomer().getId() != null)
                .collect(Collectors.groupingBy(booking -> booking.getCustomer().getId()));

        Map<Long, Invoice> invoicesByBookingId = invoiceRepository.findAll().stream()
                .filter(invoice -> invoice.getBooking() != null && invoice.getBooking().getId() != null)
                .collect(Collectors.toMap(invoice -> invoice.getBooking().getId(), Function.identity(), (left, right) -> left));

        return customers.stream()
                .map(customer -> buildCustomerSummary(customer, bookingsByCustomer, invoicesByBookingId))
                .toList();
    }

    private AdminCustomerSummary buildCustomerSummary(
            User customer,
            Map<Long, List<Booking>> bookingsByCustomer,
            Map<Long, Invoice> invoicesByBookingId
    ) {
        List<Booking> customerBookings = bookingsByCustomer.getOrDefault(customer.getId(), Collections.emptyList());

        long activeBookings = customerBookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.PENDING
                        || booking.getStatus() == BookingStatus.CONFIRMED
                        || booking.getStatus() == BookingStatus.CHECKED_IN)
                .count();

        long completedBookings = customerBookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.COMPLETED)
                .count();

        double totalSpent = customerBookings.stream()
                .mapToDouble(booking -> calculateBookingSpent(booking, invoicesByBookingId))
                .sum();

        return new AdminCustomerSummary(
                customer.getId(),
                customer.getFullName(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getIsLocked(),
                customerBookings.size(),
                activeBookings,
                completedBookings,
                totalSpent,
                customerBookings.stream()
                        .map(Booking::getCreatedAt)
                        .filter(Objects::nonNull)
                        .max(LocalDateTime::compareTo)
                        .orElse(null)
        );
    }

    private double calculateBookingSpent(Booking booking, Map<Long, Invoice> invoicesByBookingId) {
        Invoice invoice = invoicesByBookingId.get(booking.getId());

        if (invoice != null
                && invoice.getPaymentStatus() == PaymentStatus.PAID
                && invoice.getTotalAmount() != null) {
            return invoice.getTotalAmount();
        }

        if (booking.getStatus() == BookingStatus.COMPLETED && booking.getTotalPrice() != null) {
            return booking.getTotalPrice();
        }

        return 0;
    }
}
