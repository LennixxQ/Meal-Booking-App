package com.project.MealBooking.Controller;

import com.project.MealBooking.DTO.MealBookingDto;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Service.MealBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mealBooking/home")
public class MealBookingController {

    @Autowired
    private MealBookingService mealBookingService;

    @PostMapping("/book-meal")
    public ResponseEntity<List> bookMeal(@RequestHeader("Authorization")
                                                         String token,
                                                         @RequestBody MealBookingDto requestDto) throws Exception{
        String jwtToken = token.substring(7);
        List<MealBooking> bookings = mealBookingService.bookMeals(jwtToken, requestDto.getStartDate(), requestDto.getEndDate());
//        List<MealBookingDto> responseDtos = bookings.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }

//    private MealBookingDto convertToDto(MealBooking booking) {
//        MealBookingDto mealBookingDto = new MealBookingDto();
//        mealBookingDto.setBookingDate(booking.getBookingDate());
//        mealBookingDto.setUserId(booking.getUserId());
//        mealBookingDto.setEmail(booking.getEmail());
//        return mealBookingDto;
//    }


}
