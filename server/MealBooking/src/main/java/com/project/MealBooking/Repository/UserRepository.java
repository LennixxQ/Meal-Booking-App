package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <Users, String>{
    Users findByEmailAndPassword(String email, String password);
}
