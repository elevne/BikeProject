package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean UserRepository userRepository;

    @Autowired UserService userService;

    @Autowired PasswordEncoder passwordEncoder;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setUserId("elevne");
        userDTO.setPassword("12345678");
    }

    @Test
    @DisplayName("UserService 회원가입 테스트")
    public void userSignUpTest() {
        // given: userRepository Mocking
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        // when: User 회원가입 처리
        User user = userService.signUp(userDTO);
        // then: User 정보 확인
        assertEquals(user.getUsername(), userDTO.getUserId());
        assertEquals(user.getRole(), Role.ROLE_USER);
        assertTrue(passwordEncoder.matches(userDTO.getPassword(), user.getPassword()));
    }

}
