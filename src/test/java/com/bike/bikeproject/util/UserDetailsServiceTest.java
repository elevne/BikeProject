package com.bike.bikeproject.util;

import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// todo: 캐시로 바꾸게되면 이것도 전면 수정해야할듯? (지금은 UserDetailsService 가 Repository 의 기능을 수행)
@SpringBootTest
public class UserDetailsServiceTest {

    @MockBean UserRepository userRepository;
    @Autowired UserDetailsService userDetailsService;
    @Autowired PasswordEncoder passwordEncoder;

    private User user;
    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userId("elevne")
                .password("12345678!")
                .username("Wonil")
                .build();
    }

    @Test
    @DisplayName("UserDetailsService loadUserByUsername 테스트")
    public void loadUserByUsernameTest() {
        // given
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername("elevne");
        // when, then
        assertEquals(userDetails.getUsername(), "Wonil"); // todo: getUsername 메소드 바꿔야하는지 알아보기
    }

    @Test
    @DisplayName("UserDetailsService loadUserByUsername 예외 테스트")
    public void loadUserByUsernameExceptionTest() {
        // given
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        // when, then
        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername("elevne");
        });
    }

}
