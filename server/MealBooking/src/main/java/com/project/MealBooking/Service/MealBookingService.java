package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Exception.MealBookingException;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.config.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MealBookingService {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private  final UserRepository userRepository;

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    public void QuickBookMeal(String token, LocalDate bookingDate){
        Long userId = jwtService.extractUserId(token);

        if (mealBookingRepository.existsByBookingDateAndUserId(bookingDate, userId)){
            throw new MealBookingException("You have already booked a meal for tomorrow");
        }
        MealBooking mealBooking = new MealBooking();
        mealBooking.setUserId(userId);
        mealBooking.setBookingDate(bookingDate);
        mealBookingRepository.save(mealBooking);
    }

    public List<MealBooking> findAllBooking(){
        return mealBookingRepository.findAll();
    }

    public List<MealBooking> findBookingsByDate(LocalDate bookingDate){
        return mealBookingRepository.findByBookingDate(bookingDate);
    }

    public MealBooking findById(Long bookingId){
        return mealBookingRepository.findById(bookingId)
                .orElseThrow(() -> new MealBookingException("Booking not found"));
    }

    public void deleteBooking(Long bookingId){
        mealBookingRepository.deleteById(bookingId);
    }

}
