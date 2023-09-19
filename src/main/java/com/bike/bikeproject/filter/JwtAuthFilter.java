package com.bike.bikeproject.filter;

import com.bike.bikeproject.entity.JwtRefreshToken;
import com.bike.bikeproject.util.JwtUtil;
import com.bike.bikeproject.util.impl.JwtUtilImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    // todo: Filter 흐름: jwt 에서 우선 userId 검증 -> 만약 여기서 틀리면 그냥 잘못된 요청 처리 / 있으면 expired 여부 검사 / 만료되었을 경우에는 바로 그냥 refreshToken 이용해서 token 재발급해주기
    // todo: AuthHeader 에 refreshtoken 도 한 번에 붙여서 받기?
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
                    // todo: refresh token 으로 재발급
                    chain.doFilter(request, response);
                    return;
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
                                    HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException{
        try {
            JwtRefreshToken refreshToken = jwtUtil.findRefreshToken(token);
            // todo: getUsername 부터 수정 필요 (통일해야됨)
            if (jwtUtil.isRefreshTokenValid(refreshToken, userDetails.getUsername())) {
                // todo: 새로운 accessToken, refreshToken 반환
            }
            throw new RuntimeException();  // todo: refreshToken 이 valid 하지 않을 때에도 Custom Exception 만들기
        } catch (Exception e) {  // todo: findRefreshToken custom exception 만들면 여기도 그거 catch 하도록 하기
            chain.doFilter(req, resp);
            return;
        }
    }
}
