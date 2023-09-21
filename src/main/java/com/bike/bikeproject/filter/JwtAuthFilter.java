package com.bike.bikeproject.filter;

import com.bike.bikeproject.entity.JwtRefreshToken;
import com.bike.bikeproject.exception.JwtAuthException;
import com.bike.bikeproject.util.JwtUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT 인증/인가
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain chain) throws IOException, ServletException {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken, refreshToken, userId;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            // "Bearer accessToken refreshToken" 형식으로 Authorization header 받기
            String[] tokens = authHeader.substring(7).split(" ");
            accessToken = tokens[0];
            refreshToken = tokens[1];
            userId = jwtUtil.extractUserId(accessToken);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
                // userId 정보로 User 정보가 잘 조회되는지 먼저 확인 거침
                if (!userId.equals(userDetails.getUsername())) {
                    chain.doFilter(request, response);
                    return;
                }
                // accessToken 이 만료되었을 경우 같이 받은 refreshToken 을 사용하여 새로운 accessToken, refreshToken 넘겨주기
                if (jwtUtil.isTokenExpired(accessToken)) {
                    try {
                        handleExpiredToken(refreshToken, userDetails, request, response, chain);
                        return;
                    } catch (IOException | ServletException e) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " raised at JwtAuthFilter: " + e.getMessage());
        }
        chain.doFilter(request, response);
    }

    private void handleExpiredToken(String token, UserDetails userDetails,
                                    HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            JwtRefreshToken refreshToken = jwtUtil.findRefreshToken(token);
            // 만약 액세스 토큰이 기간만료된 것이라면, 새로운 액세스 토큰 생성해서 다시 보내주기 (이 때는 403 으로 나갈 것)
            if (jwtUtil.isRefreshTokenValid(refreshToken, userDetails.getUsername())) {
                String newAccessToken = jwtUtil.generateAccessToken(userDetails);
                chain.doFilter(req, resp);
                JsonObject tokenInfo = new JsonObject();
                // todo: 이 Json 처리 테스트 필요
                tokenInfo.add("accessToken", JsonParser.parseString(newAccessToken));
                String tokenInfoStr = tokenInfo.getAsString();
                resp.setContentType("application/json");
                resp.setContentLength(tokenInfoStr.length());
                resp.getOutputStream().write(tokenInfoStr.getBytes());
                return;
            }
            throw new JwtAuthException("USER ID MISMATCH IN REFRESH TOKEN: "+userDetails.getUsername());
        } catch (JwtAuthException e) {
            chain.doFilter(req, resp);
            return;
        }
    }
}
