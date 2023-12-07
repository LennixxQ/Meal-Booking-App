package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository <Users, String>{
    Users findByEmailAndPassword(String email, String password);
    Optional<Users> findByEmail(String email);

    Users findUsersByEmail(String email);
//    Users FindByEmail(String email);

    Users findByRole(@Param("role") Users.UserRole userRole);
}
