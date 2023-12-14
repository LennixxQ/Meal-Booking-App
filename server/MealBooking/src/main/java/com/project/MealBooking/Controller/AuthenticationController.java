package com.project.MealBooking.Controller;


import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.AuthenticationReponse;
import com.project.MealBooking.DTO.AuthenticationRequest;
import com.project.MealBooking.DTO.ChangePasswordRequest;
import com.project.MealBooking.DTO.LoginRequest;
import com.project.MealBooking.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mealBooking/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationReponse> register(
            @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationReponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordRequest request) {
        try {
            String jwtToken = token.substring(7);
            authenticationService.changePassword(request, jwtToken);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}