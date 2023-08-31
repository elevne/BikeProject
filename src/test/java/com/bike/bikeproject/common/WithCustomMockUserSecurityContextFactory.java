package com.bike.bikeproject.common;

import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String userId = annotation.userId();
        Role role = annotation.userRole();

        User user = User.builder()
                .userId(userId)
                .role(role)
                .build();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, "TEST_PWD", List.of(new SimpleGrantedAuthority(role.name())));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }

}
