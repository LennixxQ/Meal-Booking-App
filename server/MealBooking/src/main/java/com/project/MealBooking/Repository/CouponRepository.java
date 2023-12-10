package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByBookingId(Object mealBooking);
}
