package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface MealBookingRepository extends JpaRepository <MealBooking, Long>{
    List<MealBooking> findByUserId(Users userId);

    Optional<MealBooking> findByUserIdAndBookingDate(Users users, LocalDate bookingDate);

    @Query("SELECT b FROM MealBooking b WHERE b.bookingDate = :bookingDate AND b.email = :email")
    MealBooking findByBookingDateAndEmail(LocalDate bookingDate, String email);

    @Query("SELECT b from MealBooking b where b.userId = :userId order by b.bookingDate asc")
    List<MealBooking> findMealBookingsByUserIdOrderByBookingDateAsc(@Param("userId") Users UserID);
}
