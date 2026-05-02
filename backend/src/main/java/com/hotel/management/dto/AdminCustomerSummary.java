package com.hotel.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCustomerSummary {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private Boolean isLocked;
    private long totalBookings;
    private long activeBookings;
    private long completedBookings;
    private double totalSpent;
    private LocalDateTime lastBookingDate;
}
