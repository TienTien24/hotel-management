package com.hotel.management.model;

import com.hotel.management.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    private Double totalPrice;
}
