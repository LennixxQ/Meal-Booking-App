package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.bookingId = ?1")
    Coupon findByBookingId(Optional<MealBooking> mealBooking);

    @Query("select c from Coupon c where c.UserId = ?1")
    Coupon findByUserId(Users users);

    @Query("select c from Coupon c where c.UserId = ?1 and c.bookingId = ?2")
    Coupon findByUserIdAndBookingId(Users users, MealBooking mealBooking);

    @Query("select c from Coupon c where c.UserId = ?1 and c.couponDate = ?2")
    Coupon findByUserIdAndCouponDate(Users users, Optional<MealBooking> date);
}
