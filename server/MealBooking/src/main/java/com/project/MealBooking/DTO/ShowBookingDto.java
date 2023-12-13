package com.project.MealBooking.DTO;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowBookingDto {
    private LocalDate bookingDate;
    private Long UserId;
}
