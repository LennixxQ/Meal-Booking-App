package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
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

    @Autowired
    private  final UserRepository userRepository;

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final NotificationRepository notificationRepository;

    public List<MealBooking> bookMeals(String jwtToken, LocalDate startDate, LocalDate endDate) throws Exception{
        validateBookingDates(startDate, endDate);

        String email = jwtService.getEmailFromJwtToken(jwtToken);
        Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<MealBooking> bookings = new ArrayList<>();
        for (LocalDate bookingDate = startDate; !bookingDate.isAfter(endDate);
             bookingDate=bookingDate.plusDays(1)){
         if (isAvailableDate(bookingDate, users.getEmail())){
             var mealBooking = MealBooking.builder()
                             .email(users.getEmail())
                                     .userId(users)
                                             .bookingDate(bookingDate)
                                                             .build();
             mealBookingRepository.save(mealBooking);
             var notificationTable = NotificationTable.builder()
                     .role(users.getRole().name())
                     .userId(users)
                     .NotificationRead(false)
                     .message("Booking Successfully: "+bookingDate)
                     .build();
             notificationRepository.save(notificationTable);
         }
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
