package com.project.MealBooking.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class MealBookingDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String email;
    private Long UserId;
}
