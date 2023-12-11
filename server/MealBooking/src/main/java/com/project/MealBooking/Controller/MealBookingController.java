package com.project.MealBooking.Controller;


import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.CancelBookingRequest;
import com.project.MealBooking.DTO.MealBookingDto;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Service.CancelBookingService;
import com.project.MealBooking.Service.MealBookingService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mealBooking/home")
public class MealBookingController {

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final MealBookingService mealBookingService;

    @Autowired
    private final CancelBookingService cancelMealBooking;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CouponRepository couponRepository;

    @PostMapping("/quickMeal")
    public ResponseEntity<String> quickBookMeal(@RequestHeader ("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        mealBookingService.quickBookMeal(jwtToken);
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

    @GetMapping("/show-booking")
    public ResponseEntity<List<MealBooking>> showMealBooking(@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        Claims claims = jwtService.parseClaims(jwtToken);
        Long userID = Long.valueOf(jwtService.extractUserId(jwtToken));
        Users users = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<MealBooking> bookings = mealBookingRepository.findMealBookingsByUserIdOrderByBookingDateAsc(users);

        return ResponseEntity.ok(bookings);
    }
}




