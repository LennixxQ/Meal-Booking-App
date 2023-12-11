package com.project.MealBooking.Service.User;

import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public boolean isValidUser(String email, String password) {
        Users users = userRepository.findByEmailAndPassword(email, password);
        return users != null;
    }

    public void changePassword(String email, String newPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public String findUsersByEmail(String email) {
        try {
            Users users = userRepository.findUsersByEmail(email);
            return users.toString();
        } catch (Exception e){
            return e.toString();
        }
    }

    public String getTokenByUserId(Long userId) {
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return optionalUser.get().getUser_token();
    }
}