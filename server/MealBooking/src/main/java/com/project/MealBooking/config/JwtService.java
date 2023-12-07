package com.project.MealBooking.config;

import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ExpiredTokenException;
import com.project.MealBooking.Exception.InvalidSignatureException;
import com.project.MealBooking.Exception.MalformedTokenException;
import com.project.MealBooking.Exception.ParsingJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Service
@Configuration
public class JwtService {

    public static final String SECRET_KEY = "MeriWaliCompanyjtk6riie23435h45458in5435ur74j342346j8eu8eun8ne";
    private Users users;
//
//    @Autowired
//    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

//    *************************************************************************************
//    public String getUsernameFromToken(String token)
//
//    {
//        String username = null;
//        try {
//            Claims claims = Jwts
//                    .parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//            username = claims.getSubject();
//        } catch (Exception e) {
////            logger.error("Error parsing JWT token: {}", e.getMessage());
//            System.out.println("Error: "+e);
//        }
//        return username;
//    }

//    *****************************************************************************************
//
//    public Long getUserIdFromToken(String token) {
//        Long userId = null;
//        try {
//            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//            userId = Long.valueOf(claims.get("userId").toString());
//        } catch (Exception e) {
////            System.out.println("Error: "+e);
//        }
//        return userId;
//    }

//    ***************************************************************************************
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Claims parseClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new MalformedTokenException("Malformed JWT token");
        } catch (JwtException e) {
            throw new ParsingJwtException(e.getMessage());
        } catch (Exception e){
            throw new InvalidSignatureException("Invalid Signature Exception");
        }

    }

    public String generateToken(Users users){
        return createToken(new HashMap<>(),users);
    }

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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //Validity of token 30 mins
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public boolean isTokenValid(String token, UserDetails userDetails){
//        if (userDetails == null) {
//            return false;
//        }
//        final String username = extractUsername(token);
//        final Long userID = extractUserId(token);
//        return (username.equals(userDetails.getUsername())) && isTokenExpiried(token);
//    }
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        if (userDetails == null) {
//            System.out.println("usersDetails is Null");
//            return false;
//        } else if (token.isEmpty() || token == null) {
//            System.out.println("Token is Null");
//            return false;
//        }
//        final String username = extractUsername(token);
//
//        final Long userID = extractUserId(token);
//        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpiried(token);
//    }

    public  boolean isTokenValid(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            System.out.println("");
        }
        return false;
    }


    public boolean isTokenExpiried(String token){
        return extractExpiration(token).before(new Date());
    }

    public Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSignKey())
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.get("user_id").toString());
        } catch (Exception e) {
            throw new InvalidSignatureException("Invalid Signature Exception");
        }
    }
}

