package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.MealBookingException;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QuickMealService {
    @Autowired
    private MealBookingRepository mealBookingRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final NotificationRepository notificationRepository;


    public void quickBookMeal(String jwtToken) throws Exception{
        if (jwtToken != null) {
            String email = jwtService.getEmailFromJwtToken(jwtToken);
            Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

            Users users = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            LocalDate bookingDate = LocalDate.now().plusDays(1);

            var mealBooking = MealBooking.builder()
                            .bookingDate(bookingDate)
                                    .userId(users)
                                            .email(users.getEmail())
                                                            .build();
            mealBookingRepository.save(mealBooking);
            var notificationTable = NotificationTable.builder()
                    .role(users.getRole().name())
                    .userId(users)
                    .NotificationRead(false)
                    .message("Booking Successfully: " + bookingDate)
                    .build();
            notificationRepository.save(notificationTable);
        }
        else{
            throw new MealBookingException("Meal Cannot Booked Right Now");
        }

    }


}
