package com.hotel.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettings {
    @Id
    private Long id;

    private String hotelName;
    private String address;
    private String phone;
    private String email;
    private String logoUrl;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = 1L;
        }
    }
}
