package com.example.pethealth.components;


import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.signerKey}")
    private String secretkey;

    public String generateToken(User user){
        user.getRole();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        claims.put("role", user.getRole().getCode());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e){
            System.out.println("cannot create jwt token, errors: " + e.getMessage());
            return null;
        }
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        return Encoders.BASE64.encode(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token) // token hết hạn nó sẽ chết ở đây.
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BadRequestException("Expired JWT token");
        }
    }


    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        final Claims  claims  = this.extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //check expiration
    private boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaims(token, Claims::getExpiration) ;
        return expirationDate.before(new Date());
    }

    public String extractUsername (String token){
        return extractClaims(token, Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }
}
