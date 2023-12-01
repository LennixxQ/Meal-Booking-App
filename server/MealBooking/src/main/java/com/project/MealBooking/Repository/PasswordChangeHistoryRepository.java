package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.PasswordChangeHistory;
import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordChangeHistoryRepository extends JpaRepository<PasswordChangeHistory, Long> {
    List<PasswordChangeHistory> findByUsers(Users users);

    void deleteByUsers(Users users);
}
