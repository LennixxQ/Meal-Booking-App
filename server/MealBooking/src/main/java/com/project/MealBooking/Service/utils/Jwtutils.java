package com.project.MealBooking.Service.utils;

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

    public static final String secret = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";

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
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails){
//        Map<String, Object> extractClaims = new HashMap<>();
        return createToken(new HashMap<>(), userDetails);
    }

    //To give signature to keys
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private String createToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())  //username or useremail
//                .claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
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

