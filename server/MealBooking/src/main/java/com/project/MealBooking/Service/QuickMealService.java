package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Exception.DuplicateEmailException;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.MealBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QuickMealService {
    @Autowired
    private final MealBookingRepository mealBookingRepository;


    public MealBooking bookMeal(LocalDate bookingDate, String email, Long userId) throws Exception{
        if(bookingDate.isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Booking Date cannot be done in past");
        }
        if (mealBookingRepository.existsByBookingDateAndUserId(bookingDate, userId)){
            throw new DuplicateEmailException("User already has a booking for the choosen date");
        }
        MealBooking mealBooking = new MealBooking();
        mealBooking.setBookingDate(bookingDate);
        mealBooking.setBookingId(userId);
        mealBooking.setEmail(email);
        return mealBookingRepository.save(mealBooking);
    }
}
