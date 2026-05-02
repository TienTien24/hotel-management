package com.hotel.management.model;

import com.hotel.management.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingServiceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    @ManyToOne
    @JoinColumn(name = "service_id")
    private HotelService service;
    
    private Integer quantity;
    private LocalDateTime usedDate;
    private String note;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @PrePersist
    protected void onCreate() {
        if (usedDate == null) usedDate = LocalDateTime.now();
        if (status == null) status = ServiceStatus.PENDING;
    }
}
