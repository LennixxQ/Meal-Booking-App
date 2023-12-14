package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.bookingId = ?1")
    Coupon findByBookingId(Long mealBooking);

    @Query("SELECT c FROM Coupon c WHERE c.bookingId = :bookingId")
    Coupon findCouponByBookingId(@Param("bookingId") Long bookingId);

    @Modifying
    @Query("UPDATE Coupon c SET c.status = 'CANCELLED' WHERE c.bookingId = :bookingId")
    Coupon updateCouponStatusToCANCELLED(@Param("bookingId") Long bookingId);

}
