package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.MealBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealBookingService {

    @Autowired
    private final JwtService jwtService;


    private final MealBookingRepository mealBookingRepository;

    public List<MealBooking> bookMeals(String jwtToken, LocalDate startDate, LocalDate endDate) throws Exception{
        validateBookingDates(startDate, endDate);

        String email = jwtService.getEmailFromJwtToken(jwtToken);
        Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

        List<MealBooking> bookings = new ArrayList<>();
        for (LocalDate bookingDate = startDate; !bookingDate.isAfter(endDate);
             bookingDate=bookingDate.plusDays(1)){
         if (isAvailableDate(bookingDate, email)){
             MealBooking mealBooking = new MealBooking();
             mealBooking.setEmail(email);
             mealBooking.setUserId(userId);
             mealBooking.setBookingDate(bookingDate);
             mealBookingRepository.save(mealBooking);
         }
//         else {
//             throw new ResourceNotFoundException("Meal Booking Already Exists for Date: " +bookingDate);
//         }
        }
        return bookings;
    }

    private void validateBookingDates(LocalDate startDate, LocalDate endDate){
        if (startDate.isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Start date cannot be in the past");
        }

        if (endDate.isAfter(LocalDate.now().plusMonths(3))){
            throw new ResourceNotFoundException("Booking should not be more than 3 months");
        }
//        if (startDate.isAfter(endDate)){
//            throw new ResourceNotFoundException("Start date must be before or equal to end date");
//        }
    }

    private boolean isAvailableDate(LocalDate bookingDate, String email){
        MealBooking existBooking = findBooking(bookingDate, email);
        if (existBooking != null){
            return false;
        }

        DayOfWeek dayOfWeek = bookingDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            return false;
        }

        return true;
    }

    private MealBooking findBooking(LocalDate bookingDate, String email){
        return mealBookingRepository.findByBookingDateAndEmail(bookingDate, email);
    }
}
