package com.project.MealBooking.Service;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.redeemDto;
import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.Enums.BookingStatus;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
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

    public ResponseEntity<String> getRedeemConfirmation(redeemDto redeem, String token) {
        Long userId = Long.valueOf(jwtService.extractUserId(token));
            try {
                // User Existence
                Users user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

                // Find Booking for User
                List<MealBooking> bookings = mealBookingRepository.findByUserId(user);

                // Filter Booking by redeem Date
                Optional<MealBooking> redeemDate = bookings.stream()
                        .filter(meal -> meal.getBookingDate().equals(redeem.getBookingDate()))
                        .findFirst();

                if (redeemDate.isPresent()) {
                    // Booking found for the specified date and user
                    MealBooking mealBooking = redeemDate.get();

                    // Find the associated coupon
                    Coupon coupon = couponRepository.findByBookingId(Optional.of(mealBooking));

                    if (coupon != null) {
                        // Update the status of the coupon to REDEEMED
                        coupon.setStatus(BookingStatus.REDEEMED);

                        // Save the updated Coupon entity
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
                // Handle the exception or log an error
                e.printStackTrace();
                throw new RuntimeException("Failed to redeem booking for user and date");
            }
        }

    public ResponseEntity<String> ExpirationCoupon(redeemDto redeem, String token) {
        Long userId = Long.valueOf(jwtService.extractUserId(token));
        try {
            // User Existence
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Find Booking for User
            List<MealBooking> bookings = mealBookingRepository.findByUserId(user);

            // Filter Booking by redeem Date
            Optional<MealBooking> redeemDate = bookings.stream()
                    .filter(meal -> meal.getBookingDate().equals(redeem.getBookingDate()))
                    .findFirst();

            if (redeemDate.isPresent()) {
                // Booking found for the specified date and user
                MealBooking mealBooking = redeemDate.get();

                // Find the associated coupon
                Coupon coupon = couponRepository.findByBookingId(Optional.of(mealBooking));

                if (coupon != null) {
                    // Update the status of the coupon to REDEEMED
                    coupon.setStatus(BookingStatus.EXPIRED);

                    // Save the updated Coupon entity
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
            // Handle the exception or log an error
            e.printStackTrace();
            throw new RuntimeException("Failed to redeem booking for user and date");
        }
    }

}


//    public ResponseEntity<String> ExpiredCoupon(redeemDto redeem, String token){
//        Long userId  = Long.valueOf(jwtService.extractUserId(token));
//        try {
//            // User Existences
//            Users users = userRepository.findById(userId)
//                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//            // Find Booking for User
//            List<MealBooking> booking = mealBookingRepository.findByUserId(users);
//            System.out.println("Booking User" +booking);
////          Optional<MealBookingTable> booking = mealBookingTableRepository.findByUserIDAndBookingDate(user, redeem.getBookingDate());
//
//            // Filter Booking by redeem Date
//            Optional<MealBooking> redeemDate = booking.stream()
//                    .filter(meal -> meal.getBookingDate().equals(redeem.getBookingDate()))
//                    .findFirst();
//
//            System.out.println("RedeemDate: "+redeemDate);
//
//            Coupon couponDate = couponRepository.findByUserIdAndCouponDate(users,redeemDate);
//
//            System.out.println("CouponDate: "+couponDate.getCouponDate());
//
//            if (redeemDate.isPresent()) {
//                // Booking found for the specified date and user
//                var mealCoupon = redeemDate.get();
//
//                var redeemed = Coupon.builder()
//                        .UserId(users)
//                        .bookingId(redeemDate.get())
//                        .couponNumber(couponDate.getCouponNumber())
//                        .couponDate(couponDate.getCouponDate())
//                        .status(BookingStatus.valueOf("EXPIRED"))
//                        .build();
//
//                couponRepository.save(redeemed);
//                mealBookingRepository.delete(mealCoupon);
//                return ResponseEntity.ok("Booking Expired");
//            }
//                                             else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No booking found for the specified date and user.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ResourceNotFoundException("Failed to fetch booking for user and date");
//        }
//
//    }


