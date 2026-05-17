package com.hotel.management.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;
    private String phone;
    private String citizenId;
    private String avatar;
}
