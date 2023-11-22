package com.project.MealBooking.Service;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidUser(String email, String password) {
        Users users = userRepository.findByEmailAndPassword(email, password);
        return users != null;
    }
}


