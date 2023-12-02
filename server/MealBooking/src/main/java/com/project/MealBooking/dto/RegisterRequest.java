package com.project.MealBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private String fistname;
    private String lastname;
    private String email;
    private String password;

}
