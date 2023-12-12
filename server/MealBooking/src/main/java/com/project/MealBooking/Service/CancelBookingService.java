package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.CancelBookingRequest;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.MealBookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CancelBookingService {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Transactional
    public void cancelMealBooking(String token, CancelBookingRequest bookingDate){
        String email = jwtService.extractUsername(token);
        Long UserId = Long.valueOf(jwtService.extractUserId(token));
        LocalDate cancelDate = bookingDate.getBookingDate();

        if (cancelDate.isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Cannot Cancel Booking");
        }

        MealBooking exisitingBooking = mealBookingRepository.findByBookingDateAndEmail(cancelDate, email);

        if (exisitingBooking == null){
            throw new ResourceNotFoundException("No Booking found on Date: "+bookingDate);
        }
        mealBookingRepository.delete(exisitingBooking);

    }
}
