package com.project.MealBooking.Controller;


import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Service.MealBookingService;
import com.project.MealBooking.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mealBooking/home")
public class QuickMealBookingController {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";

    @Autowired
    private final MealBookingService mealBookingService;

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    @PostMapping("/bookMyMeal")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> bookMeal(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return ResponseEntity.ok().build();
    }
//        String email = jwtService.getUsernameFromToken(token);
//        Users users = userRepository.findUsersByEmail(email);
//
//        if (users == null){
//            throw new RuntimeException();
//        }
//
//        //Extract userID from JWT Claim
//        Long userID = jwtService.getUserIdFromToken(token);
//
//        //Check token expiration
//        if (jwtService.isTokenExpiried(token)){
//            throw new RuntimeException();
//        }
//
//        bookMealForUser(userID, LocalDate.now().plusDays(1));
//
//        return ResponseEntity.ok().build();
//    }

    private void bookMealForUser(Long userID, LocalDate bookingDate){
        MealBooking mealBooking = new MealBooking();
        mealBooking.setUserId(userID);
        mealBooking.setBookingDate(bookingDate);
//        mealBooking.setQrCode(generateQrCode());
        mealBookingRepository.save(mealBooking);
    }

}
