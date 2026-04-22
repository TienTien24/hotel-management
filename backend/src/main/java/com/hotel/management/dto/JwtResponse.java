package com.hotel.management.dto;

import com.hotel.management.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private RoleName role;
}
