package com.project.MealBooking.Controller;


import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Service.QuickMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mealBooking/home")
public class QuickMealBookingController {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";


    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final QuickMealService quickMealService;



    @PostMapping("/quickMeal")
//    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> quickBookMeal(@RequestHeader ("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        quickMealService.quickBookMeal(jwtToken);
        return ResponseEntity.ok("Your meal has been successfully booked for tomorrow!");
        }
    }


