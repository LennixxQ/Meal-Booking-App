package com.project.MealBooking.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long userId;
}
