package com.project.MealBooking.Exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
