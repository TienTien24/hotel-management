package com.hotel.management.model;

import com.hotel.management.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    private Double roomCharges;
    private Double serviceCharges;
    private Double totalAmount;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    private LocalDateTime paymentDate;
}
