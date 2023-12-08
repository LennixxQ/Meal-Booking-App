package com.project.MealBooking.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookMealRequest {
    private Date bookingDate;
    private String email;
    private Long UserID;
}
