package com.project.MealBooking.config;

import com.project.MealBooking.Entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Service
public class Jwtutils {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";

    private Users users;

    //To extract the username  // getSubject should be email or username of the user
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //Checking if the token is expiared

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //    To extract single claim for a single user
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


//

    //Used to verify the signature of the sender and it verifies that this token manipulated
    Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Users users){
//        Map<String, Object> extractClaims = new HashMap<>();
        return createToken(new HashMap<>(),users);
    }

    //To give signature to keys
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private String createToken(Map<String, Object> extraClaims, Users users){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(users.getEmail())  //username or useremail
                .claim("email", users.getEmail())
                .claim("user_id", users.getUserId())
                .claim("role",  users.getRole().name())
                .setIssuedAt(new Date(System.currentTimeMillis()))  //to check the expiration afterwards
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //Validity of token
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenExpiried(token);
    }

    public boolean isTokenExpiried(String token){
        return extractExpiration(token).before(new Date());
    }

}

