package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.CancelBookingRequest;
import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.Enums.BookingStatus;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void cancelMealBooking(String token, CancelBookingRequest bookingDate){
        String email = jwtService.extractUsername(token);
        Long UserId = Long.valueOf(jwtService.extractUserId(token));
        LocalDate cancelDate = bookingDate.getBookingDate();

        Users users = userRepository.findById(UserId)
                .orElseThrow(() -> new  ResourceNotFoundException("User with Booking cannot found"));

        if (cancelDate.isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Cannot Cancel Booking");
        }

        MealBooking exisitingBooking = mealBookingRepository.findByBookingDateAndEmail(cancelDate, email);

        if (exisitingBooking == null){
            throw new ResourceNotFoundException("No Booking found on Date: "+bookingDate);
        }
        mealBookingRepository.delete(exisitingBooking);

        Coupon coupon = couponRepository.findByBookingId(exisitingBooking.getBookingId());
        coupon.setStatus(BookingStatus.CANCELLED);

        var couponNotification = NotificationTable.builder()
                .userId(users)
                .message("Meal Cancelled Successfully: "+exisitingBooking.getBookingDate())
                .role(users.getRole().name())
                .build();

        notificationRepository.save(couponNotification);


    }
}
