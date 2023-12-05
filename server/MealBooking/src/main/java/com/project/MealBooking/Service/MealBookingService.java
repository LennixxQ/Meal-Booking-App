package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.config.Jwtutils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service

@RequiredArgsConstructor
public class MealBookingService {

    @Autowired
    private final Jwtutils jwtutils;
    private  UserRepository userRepository;
    private Users users;
    private  MealBookingRepository mealBookingRepository;

    public void bookMeal1(String token){
        Long UserID = getUserIdFromToken(token);

        //Finding Email from Repository
        Users users = userRepository.findByEmail(String.valueOf(UserID))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        MealBooking mealBooking = new MealBooking();
        mealBooking.setUserId(users);
        mealBooking.setBookingDate(Date.valueOf(LocalDate.now().plusDays(1)));
        mealBookingRepository.save(mealBooking);
    }

    private Long getUserIdFromToken(String token) {
        Claims claims = jwtutils.parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }
}
