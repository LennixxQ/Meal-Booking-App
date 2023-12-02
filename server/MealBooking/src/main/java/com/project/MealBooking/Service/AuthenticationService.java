package com.project.MealBooking.Service;


import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Repository.UserRepository;
import com.project.MealBooking.Service.utils.Jwtutils;
import com.project.MealBooking.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service


@AllArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private Jwtutils jwtutils;

    @Autowired
    private AuthenticationManager authenticationManager;

//    public AuthenticationService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    //This method will create a user save it to database and generate a token out of it
    public AuthenticationReponse register(RegisterRequest request) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password((passwordEncoder.encode(request.getPassword())))
                .role(Users.UserRole.EMPLOYEE)
                .build();
        userRepository.save(user);
        var jwtToken = jwtutils.generateToken(user);
        user.setUser_token(jwtToken);
        userRepository.save(user);
        return AuthenticationReponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    //This is safest method to authenticate user
    public AuthenticationReponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //Till now user is authenticated means email and password is correct
        //If it is correct then generate token and send it back
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Authentication Error"));
        var jwtToken = jwtutils.generateToken(user);
        return AuthenticationReponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public AuthenticationReponse login(LoginRequest request){
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        var jwtToken = jwtutils.generateToken(user);
        user.setUser_token(jwtToken);
        userRepository.save(user);
        return AuthenticationReponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public void changePassword(ChangePasswordRequest request){
        String email = request.getEmail();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(oldPassword, users.getPassword())){
            throw new RuntimeException("Invalid Old Password");
        }
        if (jwtutils.isTokenExpiried(users.getUser_token())){
            throw new RuntimeException("Toke is expired. Please Login Again");
        }

        users.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(users);
    }


}
