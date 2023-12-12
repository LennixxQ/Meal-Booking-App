package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.Enums.BookingStatus;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.MealBookingException;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    @Autowired
    private final CouponRepository couponRepository;

    public List<MealBooking> bookMeals(String jwtToken, LocalDate startDate, LocalDate endDate) throws Exception{
        validateBookingDates(startDate, endDate);
        if (endDate.isAfter(LocalDate.now().plusMonths(3))){
            throw new ResourceNotFoundException("Booking should not be more than 3 months");
        }

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

             var couponDetails = Coupon.builder()
                     .couponNumber(generateRandomCouponNumber(6))
                     .status(BookingStatus.valueOf("PENDING"))
                     .UserId(users)
                     .bookingId(mealBooking.getBookingId())
                     .build();
             couponRepository.save(couponDetails);
         }
        }
        String successMessage = "Booking Successfully!";
        return bookings.isEmpty() ? Collections.emptyList() : bookings;
    }

    public void quickBookMeal(String jwtToken) throws Exception{
        if (jwtToken != null) {
            String email = jwtService.getEmailFromJwtToken(jwtToken);
            Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

            Users users = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            LocalDate bookingDate = LocalDate.now().plusDays(1);
            if (mealBookingRepository.existsByUserIdAndBookingDate(users, bookingDate)) {
                throw new MealBookingException("Booking already exists for the specified date.");
            }

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

            var couponDetails = Coupon.builder()
                    .couponNumber(generateRandomCouponNumber(6))
                    .status(BookingStatus.valueOf("PENDING"))
                    .UserId(users)
                    .bookingId(mealBooking.getBookingId())
                    .build();
            couponRepository.save(couponDetails);
        }
        else{
            throw new MealBookingException("Meal Cannot Booked Right Now");
        }

    }

    String generateRandomCouponNumber(int length) {
        String allowedChars = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            stringBuilder.append(allowedChars.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    private void validateBookingDates(LocalDate startDate, LocalDate endDate){
        if (startDate.isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Start date cannot be in the past");
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

    public ResponseEntity<List<MealBooking>> showMealBooking(String token){
        String jwtToken = token.substring(7);
        Long userId = Long.valueOf(jwtService.extractUserId(jwtToken));

        Users users = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        List<MealBooking> mealBookings = mealBookingRepository.findByUserId(users);

        //mealBookings.get(0).setUserId(null);
    List<MealBooking> list1 = new ArrayList<>();
        for(MealBooking mealBooking1 : mealBookings)
        {
            var mealBooking = MealBooking.builder()
                            .bookingId(mealBooking1.getBookingId())
                                    .bookingDate(mealBooking1.getBookingDate())
                                            .timestamp(mealBooking1.getTimestamp())
                    .email(mealBooking1.getEmail())
                                                    .build();
            list1.add(mealBooking);

        }



        return ResponseEntity.ok(list1);

    }

}
