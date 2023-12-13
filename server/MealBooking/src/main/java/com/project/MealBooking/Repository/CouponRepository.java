package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.bookingId = ?1")
    Coupon findByBookingId(Long mealBooking);

}
