package com.romit.workouttracker.services;

import com.romit.workouttracker.entities.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JWTService {
    private final SecretKey secretKey;

    public JWTService(@Value("${jwt.secret.key}") String key) throws NoSuchAlgorithmException {
        if (key == null || key.isEmpty()) {
            System.err.println("No JWT secret key provided, generating a secure random key.");
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            this.secretKey = keyGen.generateKey();
            return;
        }
        this.secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
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
