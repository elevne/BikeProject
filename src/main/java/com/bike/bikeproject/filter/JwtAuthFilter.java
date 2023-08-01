package com.bike.bikeproject.filter;

import com.bike.bikeproject.component.JwtUtil;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil            jwtUtil;

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain chain) throws IOException, ServletException {
        // 임시 TEST 용으로 작성 (JWT 구현 전까지 사용할 것)
        // todo: JWT Auth Filter 처리 (JwtUtil 작성)
        try {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername("elevne");
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            token.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request)
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
        return;
    }
}
