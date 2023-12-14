package com.project.MealBooking.Controller;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.CouponResponseDto;
import com.project.MealBooking.DTO.redeemDto;
import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/mealBooking/coupon")
@RequiredArgsConstructor
public class CouponController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MealBookingRepository mealBookingRepository;

    @Autowired
    private final CouponRepository couponRepository;

    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final CouponService couponService;

    @GetMapping("/redeem/{bookingDate}")
    public ResponseEntity<CouponResponseDto> sendCoupon(@RequestHeader("Authorization") String jwtToken,
                                                        @PathVariable LocalDate bookingDate)
            throws Exception {
        String token = jwtToken.substring(7);
        String email = jwtService.getEmailFromJwtToken(token);
        Long UserId = Long.valueOf(jwtService.extractUserId(token));

        Users users = userRepository.findById(UserId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot Find User"));

        Optional<MealBooking> mealBooking = mealBookingRepository.findByUserIdAndBookingDate(users, bookingDate);

        Coupon coupon = couponRepository.findByBookingId(mealBooking.get().getBookingId());

        var couponReedem = CouponResponseDto.builder()
                .userId(users.getUserId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .bookingId(mealBooking.get().getBookingId())
                .bookingDate(mealBooking.get().getBookingDate())
                .Coupon(coupon.getCouponNumber())
                .build();
            return ResponseEntity.ok(couponReedem);
    }

    @GetMapping("/coupon-status")
    private ResponseEntity<String> RedeemStatus(@RequestHeader("Authorization") String token,
                                              @RequestBody redeemDto redeemDto) throws Exception{

        String jwtToken = token.substring(7);
        var couponRedeem = couponService.getRedeemConfirmation(redeemDto,jwtToken);
        if (couponRedeem.getStatusCode().is2xxSuccessful() ) {
            return ResponseEntity.ok().body("Redeemed Successfully!");
        }
        else {
            return ResponseEntity.badRequest().body("Coupon Doesn't Exist");
        }
    }

    @GetMapping("/expired-coupon")
    private ResponseEntity<String> ExpiredStatus(@RequestHeader("Authorization") String token,
                                                 @RequestBody redeemDto redeemDto) throws Exception{
        String jwtToken = token.substring(7);
        var couponExpired = couponService.ExpirationCoupon(redeemDto, jwtToken);
        if (couponExpired.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Coupon Expired");
        }
        else {
            return ResponseEntity.badRequest().body("Coupon Doesn't Exist");
        }
    }
}