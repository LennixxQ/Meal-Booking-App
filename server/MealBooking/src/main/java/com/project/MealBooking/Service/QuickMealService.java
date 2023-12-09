package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Exception.MealBookingException;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QuickMealService {
    @Autowired
    private MealBookingRepository mealBookingRepository;

    @Autowired
    private final JwtService jwtService;


    public void quickBookMeal(String jwtToken) throws Exception{
        if (jwtToken != null) {
            String email = jwtService.getEmailFromJwtToken(jwtToken);
            Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

            LocalDate bookingDate = LocalDate.now().plusDays(1);

            MealBooking mealBooking = new MealBooking();
            mealBooking.setBookingDate(bookingDate);
            mealBooking.setUserId(userId);
            mealBooking.setEmail(email);
            mealBookingRepository.save(mealBooking);
        }
        else{
            throw new MealBookingException("Meal Cannot Booked Right Now");
        }

    }


}
