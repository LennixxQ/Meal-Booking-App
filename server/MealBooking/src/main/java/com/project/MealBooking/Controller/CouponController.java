package com.project.MealBooking.Controller;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.CouponResponseDto;
import com.project.MealBooking.DTO.NotificationResponse;
import com.project.MealBooking.DTO.redeemDto;
import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Coupon coupon = couponRepository.findByBookingId(mealBooking);

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

        @GetMapping("/notification/{userId}")
        public ResponseEntity<List<NotificationResponse>> sendNotification(@RequestHeader("Authorization") String token,
                                                                           @PathVariable Long userId) throws Exception {
            String jwtToken = token.substring(7);
            Long UserId = Long.valueOf(jwtService.extractUserId(jwtToken));

            Users users = userRepository.findById(UserId)
                    .orElseThrow(() -> new ResourceNotFoundException("User Cannot be find"));

            List<NotificationTable> notificationTable = notificationRepository.findAllByUserId(users);

            if (notificationTable != null && !notificationTable.isEmpty()) {
                List<NotificationResponse> notificationDtos = notificationTable.stream()
                        .map(notification -> new NotificationResponse(
                                notification.getUserId().getUserId(),
                                notification.getMessage()))
                        .collect(Collectors.toList());
                System.out.printf("Number of Notification: "+notificationDtos.size());

                return ResponseEntity.ok(notificationDtos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @GetMapping("/coupon-status")
    private ResponseEntity<String> RedeemStatus(@RequestHeader("Authorization") String token,
                                              @RequestBody redeemDto redeemDto) throws Exception{

        String jwtToken = token.substring(7);
        couponService.getRedeemConfirmation(redeemDto,jwtToken);
        return ResponseEntity.ok().body("Redeem Successfully!");
    }

    @GetMapping("/expired-coupon")
    private ResponseEntity<String> ExpiredStatus(@RequestHeader("Authorization") String token,
                                                 @RequestBody redeemDto redeemDto) throws Exception{
        String jwtToken = token.substring(7);
        couponService.ExpirationCoupon(redeemDto, jwtToken);
        return ResponseEntity.ok("Booking Expired");
    }
}
