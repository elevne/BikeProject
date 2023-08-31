package com.bike.bikeproject.common;

import com.bike.bikeproject.entity.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    String userId() default "TEST_ID";

    Role userRole() default Role.ROLE_USER;

}
