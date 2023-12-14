package com.project.MealBooking.Repository;

import com.project.MealBooking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <Users, Long>{
    Users findByEmailAndPassword(String email, String password);

    Optional<Users> findByEmail(String email);

    Users findUsersByEmail(String email);

    Users findByRole(@Param("role") Users.UserRole userRole);

    @Query("select u from Users u where u.UserId = ?1")
    Users findUsersByUserId(Long userId);
}
