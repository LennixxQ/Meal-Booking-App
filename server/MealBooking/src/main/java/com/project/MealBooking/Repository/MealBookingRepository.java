package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.MealBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MealBookingRepository extends JpaRepository <MealBooking, Long>{

}
