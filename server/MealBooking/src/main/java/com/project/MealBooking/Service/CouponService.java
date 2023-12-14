package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.redeemDto;
import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.Enums.BookingStatus;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final CouponRepository couponRepository;
    private final NotificationRepository notificationRepository;

    public ResponseEntity<String> getRedeemConfirmation(redeemDto redeem, String token) {
        Long userId = Long.valueOf(jwtService.extractUserId(token));
            try {

                Users user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

                List<MealBooking> bookings = mealBookingRepository.findByUserId(user);

                Optional<MealBooking> redeemDate = bookings.stream()
                        .filter(meal -> meal.getBookingDate().equals(redeem.getBookingDate()))
                        .findFirst();

                if (redeemDate.isPresent()) {
                    MealBooking mealBooking = redeemDate.get();

                    Coupon coupon = couponRepository.findByBookingId(mealBooking.getBookingId());

                    if (coupon != null) {
                        coupon.setStatus(BookingStatus.REDEEMED);

                        couponRepository.save(coupon);
                        mealBookingRepository.delete(mealBooking);

                        return ResponseEntity.ok("Booking Redeemed successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coupon not found for the specified booking.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No booking found for the specified date and user.");
                }
            } catch (Exception e) {

                e.printStackTrace();
                throw new RuntimeException("Failed to redeem booking for user and date");
            }
        }

    public ResponseEntity<String> ExpirationCoupon(redeemDto redeem, String token) {
        Long userId = Long.valueOf(jwtService.extractUserId(token));
        try {

            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            List<MealBooking> bookings = mealBookingRepository.findByUserId(user);

            Optional<MealBooking> redeemDate = bookings.stream()
                    .filter(meal -> meal.getBookingDate().equals(redeem.getBookingDate()))
                    .findFirst();

            if (redeemDate.isPresent()) {
                MealBooking mealBooking = redeemDate.get();

                Coupon coupon = couponRepository.findByBookingId(mealBooking.getBookingId());

                if (coupon != null) {
                    coupon.setStatus(BookingStatus.EXPIRED);
                    couponRepository.save(coupon);
                    mealBookingRepository.delete(mealBooking);

                    return ResponseEntity.ok("Booking is Expired");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coupon not found for the specified booking.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No booking found for the specified date and user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get booking for user and date");
        }
    }

}