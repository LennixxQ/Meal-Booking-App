package com.project.MealBooking.Exception;

public class ParsingJwtException extends RuntimeException {
    public ParsingJwtException(String message) {
        super("Error parsing JWT claims: " + message);
    }
}
