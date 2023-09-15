package com.bike.bikeproject.controller;

import com.bike.bikeproject.config.SecurityConfig;
import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.filter.JwtAuthFilter;
import com.bike.bikeproject.service.UserService;
import com.bike.bikeproject.util.JwtUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = AuthController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthFilter.class})
        }
)
public class AuthControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean UserService userService;

    @MockBean UserDetailsService userDetailsService;

    @MockBean JwtUtil jwtUtil;

    private User user;
    private UserDTO userDTO;
    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userId("cwi5525")
                .password("pwd1234!")
                .username("elevne")
                .build();
        userDTO = new UserDTO("cwi5525", "pwd1234!", "elevne");
    }

    @Test
    @DisplayName("회원가입 API 호출 테스트")
    public void signUpTest() throws Exception {
        // given
        when(userService.signUp(any()))
                .thenReturn(user);
        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/auth/signup")
                        .content(new Gson().toJson(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.username").value(user.getUsername()));
        verify(userService, times(1)).signUp(any());
    }

}
