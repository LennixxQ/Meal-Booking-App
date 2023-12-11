package com.project.MealBooking.DTO;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponseDto {
    String firstName;
    String lastName;
    Long bookingId;
    Long userId;
    LocalDate bookingDate;
    String Coupon;
}
