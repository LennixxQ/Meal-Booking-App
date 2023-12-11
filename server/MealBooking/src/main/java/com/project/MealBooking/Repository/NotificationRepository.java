package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationTable, Long> {
   List<NotificationTable> findAllByUserId(Users userId);
}
