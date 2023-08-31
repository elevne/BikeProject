package com.bike.bikeproject.filter;

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

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain chain) throws IOException, ServletException {
        // todo: JWT 예외 상황 등등 계속 고쳐나가야 함.
        final String authHeader = request.getHeader("Authorization");
        final String jwt, userId;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            jwt = authHeader.substring(7);
            userId = jwtUtil.extractUserId(jwt);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername("elevne");
                if (jwtUtil.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " raised at JwtAuthFilter: " + e.getMessage());
        }
        chain.doFilter(request, response);
    }
}
