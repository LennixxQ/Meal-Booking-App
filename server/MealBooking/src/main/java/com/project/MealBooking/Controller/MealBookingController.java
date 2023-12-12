package com.project.MealBooking.Controller;


import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.CancelBookingRequest;
import com.project.MealBooking.DTO.MealBookingDto;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Service.CancelBookingService;
import com.project.MealBooking.Service.MealBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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
    public ResponseEntity<List<LocalDate>> bookMeal(@RequestHeader("Authorization")
                                         String token,
                                                    @RequestBody MealBookingDto requestDto) throws Exception{
        String jwtToken = token.substring(7);
        List<MealBooking> bookings = mealBookingService.bookMeals(jwtToken, requestDto.getStartDate(), requestDto.getEndDate());
        List<LocalDate> bookedDates = bookings.stream()
                .map(MealBooking::getBookingDate)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookedDates);
    }

    @DeleteMapping("/cancel-booking")
    public ResponseEntity<String> cancelMealBooking(@RequestHeader("Authorization")
                                                      String token,
                                                  @RequestBody CancelBookingRequest bookingDate) throws Exception{
        String jwtToken = token.substring(7);
        cancelMealBooking.cancelMealBooking(jwtToken, bookingDate);

        return ResponseEntity.ok().body("Booking Cancelled Successfully");
    }

    @GetMapping("/showbookings")
    public ResponseEntity<?> showMealBooking(@RequestHeader("Authorization")
                                                                 String token){
        ResponseEntity<List<MealBooking>> bookings = mealBookingService.showMealBooking(token);
        return bookings;
    }
}




