package com.hotel.management.model;

import com.hotel.management.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    private Boolean isLocked = false;
}
