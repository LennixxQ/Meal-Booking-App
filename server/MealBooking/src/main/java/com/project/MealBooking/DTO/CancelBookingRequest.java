package com.project.MealBooking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class CancelBookingRequest {
    LocalDate bookingDate;
}
