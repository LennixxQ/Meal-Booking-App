package com.project.MealBooking.Service;


import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.*;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.DuplicateEmailException;
import com.project.MealBooking.Exception.InvalidPasswordException;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
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

    private final UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private final NotificationRepository notificationRepository;



    //This method will create a user save it to database and generate a token out of it
    public AuthenticationReponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateEmailException("Email Already Exists");
        }
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password((passwordEncoder.encode(request.getPassword())))
                .role(Users.UserRole.ROLE_EMPLOYEE)
                .build();
        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(saveduser);
        var savedUserWithToken = userRepository.save(user);
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
                .orElseThrow(() -> new ResourceNotFoundException("Authentication Error"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationReponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public AuthenticationReponse login(LoginRequest request){
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Invalid Password");
        }
        var jwtToken = jwtService.generateToken(user);
        user.setUser_token(jwtToken);
        userRepository.save(user);
        return AuthenticationReponse.builder()
                .jwtToken(jwtToken)
//                .withSubject(user.getEmail(), user.getRole(), user.getEmail())
                .build();
    }

    public void changePassword(ChangePasswordRequest request, String token) throws Exception {
        Long userId = Long.valueOf(jwtService.extractUserId(token));
        String email = jwtService.getEmailFromJwtToken(token);
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID cannot find"));

        if(!passwordEncoder.matches(oldPassword, users.getPassword())){
            throw new InvalidPasswordException("Invalid Old Password");
        }
        if (jwtService.isTokenExpiried(users.getUser_token())){
            throw new ResourceNotFoundException("Token is expired. Please Login Again");
        }

        Users usersEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

            users.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(users);
            var changePassword = NotificationTable
                    .builder()
                    .userId(users)
                    .NotificationRead(false)
                    .role(users.getRole().name())
                    .message("Your password change successfully")
                    .build();
            notificationRepository.save(changePassword);

    }


}