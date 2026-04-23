package com.hotel.management.model;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentMethod;
import com.hotel.management.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    private String guestFullName;
    private String guestPhone;
    private String guestEmail;
    private String guestAddress;
    private Integer numberOfGuests;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    private Double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;
    private String reviewTitle;
    private Integer reviewRating;

    @Column(columnDefinition = "TEXT")
    private String reviewComment;

    private LocalDateTime reviewCreatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
