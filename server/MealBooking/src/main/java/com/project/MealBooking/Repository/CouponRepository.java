package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Coupon;
import com.project.MealBooking.Entity.MealBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByBookingId(Optional<MealBooking> mealBooking);
}
