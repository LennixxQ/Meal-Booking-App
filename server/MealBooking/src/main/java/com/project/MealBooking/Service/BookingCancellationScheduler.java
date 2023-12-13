package com.project.MealBooking.Service;

import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.Enums.BookingStatus;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Repository.CouponRepository;
import com.project.MealBooking.Repository.MealBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingCancellationScheduler {

    @Autowired
    private MealBookingRepository mealBookingRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void cancelExpiredBookings(){
        List<MealBooking> allBookings = new ArrayList<MealBooking>();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (MealBooking booking : allBookings){
            if (booking.getBookingDate().isAfter(currentDate)
                    || (booking.getBookingId().equals(currentDate) && currentTime.isBefore(LocalTime.of(8, 0))))
            {
                    mealBookingRepository.delete(booking);
            }

            Coupon coupon = couponRepository.findByBookingId(booking.getBookingId());

            if (coupon != null) {
                coupon.setStatus(BookingStatus.EXPIRED);
                couponRepository.save(coupon);
            }
        }
    }
}
