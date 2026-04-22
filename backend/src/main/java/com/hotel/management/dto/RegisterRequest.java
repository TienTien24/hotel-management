package com.hotel.management.dto;

import com.hotel.management.enums.RoleName;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private RoleName role;
}
