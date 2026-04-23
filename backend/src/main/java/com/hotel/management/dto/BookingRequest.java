package com.hotel.management.dto;

import com.hotel.management.enums.PaymentMethod;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private String guestFullName;
    private String guestPhone;
    private String guestEmail;
    private String guestAddress;
    private PaymentMethod paymentMethod;
}
