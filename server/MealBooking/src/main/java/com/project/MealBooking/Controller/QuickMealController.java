package com.project.MealBooking.Controller;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Service.MealBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

//Without JWT
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/QuickMeal")
public class QuickMealController {

    @Autowired
    private final MealBookingService mealBookingService;

    public ResponseEntity <String> bookQuickMeal(@RequestBody com.project.MealBooking.dto.BookMealRequest request){
        try {
            LocalDate bookingDate = request.getBookingDate();
            String email = request.getEmail();
            Long UserID = request.getUserID();

            MealBooking mealBooking = new MealBooking();
            mealBooking.setBookingDate(bookingDate);
            mealBooking.setEmail(email);
            mealBooking.setUserId(UserID);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "Meal Booked Successfully");
            response.put("data", bookingDate);

            return ResponseEntity.ok("Meal Booked !!!");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
