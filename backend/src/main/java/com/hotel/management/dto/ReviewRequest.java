package com.hotel.management.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private String guestFullName;
    private String title;
    private Integer rating;
    private String comment;
}
