package com.nicolas.hotelreservation.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class TokenProvider {
    @Value("${jwt.expiration-days}")
    private long tokenExpiration;
    @Value("${jwt.key}")
    private String key;

    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return buildToken(user.getUsername());
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    private String buildToken(String username) {
        Instant expirationInstant = LocalDateTime.now()
                .plusDays(tokenExpiration)
                .toInstant(ZoneOffset.of("-03:00"));

        return Jwts.builder()
                .issuer("hotel-reservation-api")
                .subject(username)
                .issuedAt(new Date())
                .expiration(Date.from(expirationInstant))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try{
            getClaims(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
