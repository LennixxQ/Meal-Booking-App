package com.project.MealBooking.Configuration;

import com.project.MealBooking.Entity.Users;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";

    @Autowired
    private final JwtService jwtService;

    private UserDetails userDetails;

    @Autowired
    private final UserDetailsService userDetailsService; //to extract the user from the database

    private UserDetails getUserDetails(String token){
        Users getUserDetails = new Users();
        Claims claims = jwtService.parseClaims(token);
        String subject = claims.getSubject();
        String role = (String) claims.get("role");
        Integer UserID = (Integer) claims.get("user_id");
        Long userID = Long.valueOf(jwtService.extractUserId(token));
        System.out.println("Subject: " +subject);
        System.out.println("Roles: " +role);
        System.out.println("UserID: "+UserID);
        System.out.println("userID: "+userID);
        getUserDetails.setRole(Users.UserRole.valueOf(role));
        String[] jwtSubject = subject.split(",");
//        getUserDetails.setEmail(jwtSubject[1]);
//        getUserDetails.setUserId(UsersID);
        return getUserDetails;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwtToken;
        final String userEmail;

        jwtToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwtToken);
            if (!jwtService.isTokenValid(jwtToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token.");
                return;
            }
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails =userDetailsService.loadUserByUsername(userEmail);
//                if (userDetails == null) {
//                    userDetails = getUserDetails(jwtToken);
//                }
                if (jwtService.isTokenValid(jwtToken)) {
                    Long userID = Long.valueOf(jwtService.extractUserId(jwtToken));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    SecurityContextHolder.getContext().setAuthentication(null);
                    logger.warn("UserDetails is null. Continuing execution without setting authentication.");
                }

            }
            filterChain.doFilter(request, response);
    }
}
