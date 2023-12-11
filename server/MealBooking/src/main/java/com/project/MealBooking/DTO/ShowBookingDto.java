package com.project.MealBooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowBookingDto {
    private LocalDate bookingDate;
    private String email;
    private Long UserId;
}
