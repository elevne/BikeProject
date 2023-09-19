package com.bike.bikeproject.util;

import com.bike.bikeproject.entity.JwtRefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtil {

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractUserId(String token);

    boolean isTokenExpired(String token);

    JwtRefreshToken findRefreshToken(String token);

    boolean isRefreshTokenValid(JwtRefreshToken refreshToken, String userId);

}
