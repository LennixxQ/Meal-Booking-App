package com.project.MealBooking.Controller;


import com.project.MealBooking.Service.AuthenticationService;
import com.project.MealBooking.Service.utils.Jwtutils;
import com.project.MealBooking.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @Autowired
    private Jwtutils jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationReponse> register(
            @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationReponse> register(
            @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> TestHello(){
        return ResponseEntity.ok("Hello From Backend!!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationReponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        try{
            authenticationService.changePassword(request);
            return ResponseEntity.ok("Password Successfully Changed");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
