package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.config.JwtAuthenticationFilter;
import com.project.MealBooking.config.JwtService;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    //Yet to Completely handle the exception
    public void quickBookMeal(String jwtToken) throws Exception{
        String email = jwtService.getEmailFromJwtToken(jwtToken);
        Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

        LocalDate bookingDate = LocalDate.now().plusDays(1);

        MealBooking mealBooking = new MealBooking();
            mealBooking.setBookingDate(bookingDate);
            mealBooking.setUserId(userId);
            mealBooking.setEmail(email);
            mealBookingRepository.save(mealBooking);

    }


}
