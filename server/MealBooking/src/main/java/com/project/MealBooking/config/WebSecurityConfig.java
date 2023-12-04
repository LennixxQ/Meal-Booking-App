package com.project.MealBooking.config;

import com.project.MealBooking.Service.jwt.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private JwtAuthenticationFilter jwtAuthFilter;

    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
//                .cors()
//                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**") //.hasRole("EMPLOYEE")
//                .requestMatchers("/api/v1/auth/register").hasRole("Admin")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                Authentication state or session state should not be stored after every request
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(userDetailsServiceImpl.authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        CorsConfigurationSource corsConfigurationSource = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOrigin("*"); //allow all origins
                corsConfiguration.addAllowedMethod("*"); //allow all origins
                corsConfiguration.addAllowedHeader("*"); //allow all origins
                return corsConfiguration;
            }
        };

        http.cors(with -> with.configurationSource(corsConfigurationSource));

        return http.build();
    }

}
