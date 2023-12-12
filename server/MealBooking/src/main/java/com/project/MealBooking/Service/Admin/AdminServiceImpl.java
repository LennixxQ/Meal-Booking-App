package com.project.MealBooking.Service.Admin;

import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Configuration.JwtService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {

    @Autowired
    public final UserRepository userRepository;

    private JwtService jwtService;

    public <passwordEncoded> AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct  //Method will invoke automatically without calling API
    public void createAdminAccount(){
        Users adminAccount = userRepository.findByRole(Users.UserRole.ROLE_ADMIN);
        if (adminAccount == null) {
            Users admin = new Users();
            admin.setFirstName("Vivek");
            admin.setLastName("Chauhan");
            admin.setEmail("vivek123@gmail.com");
            admin.setRole(Users.UserRole.ROLE_ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(admin);
        }
    }

}
