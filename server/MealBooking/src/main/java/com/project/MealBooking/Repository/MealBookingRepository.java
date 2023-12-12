package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.MealBooking;
import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface MealBookingRepository extends JpaRepository <MealBooking, Long>{
    List<MealBooking> findByUserId(Users userId);

    @Query("select (count(m) > 0) from MealBooking m where m.userId = ?1 and m.bookingDate = ?2")
    boolean existsByUserIdAndBookingDate(Users users, LocalDate bookingDate);

    Optional<MealBooking> findByUserIdAndBookingDate(Users users, LocalDate bookingDate);

    @Query("SELECT b FROM MealBooking b WHERE b.bookingDate = :bookingDate AND b.email = :email")
    MealBooking findByBookingDateAndEmail(LocalDate bookingDate, String email);


    @Query("select m from MealBooking m where m.userId = ?1 order by m.bookingDate")
    List<MealBooking> findMealBookingsByUserIdOrderByBookingDateAsc(Users UserID);
}
