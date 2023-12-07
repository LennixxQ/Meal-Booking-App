package com.project.MealBooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookMealRequest {
    private LocalDate bookingDate;
    private String email;
    private Long UserID;
}
