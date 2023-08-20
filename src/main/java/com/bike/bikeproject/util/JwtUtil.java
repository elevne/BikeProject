package com.bike.bikeproject.util;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtil {

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

}
