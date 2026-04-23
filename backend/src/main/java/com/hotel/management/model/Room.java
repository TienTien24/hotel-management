package com.hotel.management.model;

import com.hotel.management.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String category;
    private String type;
    private Integer capacity;
    private Double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
}
