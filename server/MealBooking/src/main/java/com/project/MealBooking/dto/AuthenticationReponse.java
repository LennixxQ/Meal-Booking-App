package com.project.MealBooking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationReponse {
    private String jwtToken;

    public AuthenticationReponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
