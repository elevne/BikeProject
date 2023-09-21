package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.entity.JwtRefreshToken;
import com.bike.bikeproject.exception.JwtAuthException;
import com.bike.bikeproject.repository.JwtRefreshTokenRepository;
import com.bike.bikeproject.repository.UserRepository;
import com.bike.bikeproject.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtilImpl implements JwtUtil {

    private final UserRepository userRepository;

    private final JwtRefreshTokenRepository jwtRepository;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    @Transactional
    public String generateRefreshToken(UserDetails userDetails) {
        String token = UUID.randomUUID().toString();
        JwtRefreshToken refreshToken = JwtRefreshToken.builder()
                .token(token)
                .expiryDate(Instant.now().plusMillis(1000 * 60 * 60 * 24 * 7))
                .userId(userDetails.getUsername())
                .build();
        jwtRepository.save(refreshToken);
        return token;
    }

    @Override
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public JwtRefreshToken findRefreshToken(String token) {
        return jwtRepository.findByToken(token)
                .orElseThrow(() -> new JwtAuthException("REFRESH TOKEN NOT FOUND: " + token));
    }

    @Override
    public boolean isRefreshTokenValid(JwtRefreshToken refreshToken, String userId) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            jwtRepository.delete(refreshToken);
            return false;
        }
        return userId.equals(refreshToken.getUserId());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return Objects.requireNonNull(claimsResolver.apply(claims));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
