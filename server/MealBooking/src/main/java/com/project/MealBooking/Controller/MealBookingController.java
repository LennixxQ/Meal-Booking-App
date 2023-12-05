package com.project.MealBooking.Controller;


import com.project.MealBooking.Service.MealBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mealBooking/auth/home")
public class MealBookingController {

    @Autowired
    private final MealBookingService mealBookingService;

    @PostMapping("/bookMyMeal")
    public ResponseEntity<String> bookMeal(@RequestHeader("Authorization") String token){
        try {
            mealBookingService.bookMeal1(token);
            return ResponseEntity.ok("Meal Booked Successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Booking Meal: "+e.getMessage());
        }
    }
}
