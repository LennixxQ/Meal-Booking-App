//package com.project.MealBooking.Service.admin;
//
//import com.project.MealBooking.Entity.Users;
//import com.project.MealBooking.Repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdminServiceImpl {
//
//    @Autowired
//    public final UserRepository userRepository;
//
//    public <passwordEncoded> AdminServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @PostConstruct  //Method will invoke automatically without calling API
//    public void createAdminAccount(){
//        Users adminAccount = userRepository.findByRole(Users.UserRole.Admin);
//        if (adminAccount == null) {
//            Users admin = new Users();
//            admin.setEmail("vivek123@gmail.com");
//            admin.setPassword("admin");
//            admin.setRole(Users.UserRole.Admin);
//            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
//            userRepository.save(admin);
//        }
//    }
//
//}
