package com.dacloud.pgw.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtils {
   private static final long EXPIRATION_TIME_MILLIS = 86400000;
   private final SecretKey secretKey;

   public JwtUtils() {
      final var secretString = "auDWN5qNGsiGaH4Q2S7S9yq8TqvFJlbpMeReXhPiDP9TjCCIccBWojls0Ygaxuhm";
      final var keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
      this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
   }

   public String generateToken(UserDetails userDetails) {
      return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
            .signWith(secretKey)
            .compact();
   }

   public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
      return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
            .signWith(secretKey)
            .compact();
   }

   public String extractUsername(String token) {
      return extractClaims(token, Claims::getSubject);
   }

   private <T> T extractClaims(String token, Function<Claims, T> claimsFunction) {
      return claimsFunction.apply(
            Jwts.parser()
                  .verifyWith(secretKey)
                  .build()
                  .parseSignedClaims(token)
                  .getPayload());
   }

   public boolean isTokenValid(String token, UserDetails userDetails) {
      final var username = extractUsername(token);
      return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
   }

   public boolean isTokenExpired(String token) {
      return extractClaims(token, Claims::getExpiration).before(new Date());
   }
}
