package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.NotificationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationTable, Long> {
}
