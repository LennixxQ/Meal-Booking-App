package com.project.MealBooking.config;

import com.project.MealBooking.Entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.project.MealBooking.Entity.Role;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";
    private final Jwtutils jwtutils;
    private UserDetails userDetails;
    private final UserDetailsService userDetailsService; //to extract the user from the database

    private UserDetails getUserDetails(String token){
        Users UserDetails = new Users();
        Users users = new Users();
        UserDetails userDetails = users;
        Claims claims = (Claims) jwtutils.extractAllClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");
        System.out.println("Email: " +subject);
        System.out.println("Roles: " +roles);
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        for (String aRoleNames : roleNames){
            users.addRole(new Role(aRoleNames).toString());
        }
        String[] jwtSubject = subject.split(",");

//        userDetails.setId(Integer.parseInt(jwtSubject[0]));
        users.setEmail(jwtSubject[1]);

        return users;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader("Autorization");
        final String jwtToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        // validating token
        boolean isValid = false;
        try
        {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
            isValid = true;
        }
        catch(Exception e)
        {
            logger.error("JWT is invalid");
        }

        if(!isValid)
        {
            filterChain.doFilter(request,response);
            return;
        }

        //To extract email from JWT token we need a class that manipulate JWT token
        userEmail = jwtutils.extractUsername(jwtToken);

        //Check if userEmail is not null it will extract userEmail from the token
        //And check if user is not authenticated by SecurityContextholder obj
        //And if it is null that means user is not yet authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtutils.isTokenValid(jwtToken, userDetails)){

                //To update our security Context after authentication
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
