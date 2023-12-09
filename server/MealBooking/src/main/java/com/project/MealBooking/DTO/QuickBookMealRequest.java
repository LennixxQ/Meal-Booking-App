package com.project.MealBooking.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class QuickBookMealRequest {
    private Date bookingDate;
    private String email;
    private Long UserID;
}
