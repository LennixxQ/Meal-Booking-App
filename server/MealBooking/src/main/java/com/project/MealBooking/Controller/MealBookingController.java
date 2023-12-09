package com.project.MealBooking.Controller;


import com.project.MealBooking.DTO.CancelBookingRequest;
import com.project.MealBooking.DTO.MealBookingDto;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Service.CancelBookingService;
import com.project.MealBooking.Service.MealBookingService;
import com.project.MealBooking.Service.QuickMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mealBooking/home")
public class MealBookingController {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final QuickMealService quickMealService;

    @Autowired
    private final MealBookingService mealBookingService;

    @Autowired
    private final CancelBookingService cancelMealBooking;

    private CancelBookingRequest cancelBookingRequest;

    @PostMapping("/quickMeal")
    public ResponseEntity<String> quickBookMeal(@RequestHeader ("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        quickMealService.quickBookMeal(jwtToken);
        return ResponseEntity.ok("Your meal has been successfully booked for tomorrow!");
        }

    @PostMapping("/book-meal")
    public ResponseEntity<List> bookMeal(@RequestHeader("Authorization")
                                         String token,
                                         @RequestBody MealBookingDto requestDto) throws Exception{
        String jwtToken = token.substring(7);
        List<MealBooking> bookings = mealBookingService.bookMeals(jwtToken, requestDto.getStartDate(), requestDto.getEndDate());
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/cancel-booking")
    public ResponseEntity<String> cancelMealBooking(@RequestHeader("Authorization")
                                                      String token,
                                                  @RequestBody CancelBookingRequest bookingDate) throws Exception{
        String jwtToken = token.substring(7);
        cancelMealBooking.cancelMealBooking(jwtToken, bookingDate);

        return ResponseEntity.ok().body("Booking Cancelled Successfully");
    }

}




