package com.romit.workouttracker.services;

import com.romit.workouttracker.entities.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private final SecretKey secretKey;

    public JWTService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        secretKey = keyGen.generateKey();
    }

    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = secretKey.getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        Date expiry = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getExpiration();
        if (expiry.before(new java.util.Date()))
            return false;

        String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername());
    }
}
