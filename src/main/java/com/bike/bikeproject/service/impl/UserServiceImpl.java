package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.dto.JwtDTO;
import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.exception.JwtAuthException;
import com.bike.bikeproject.repository.UserRepository;
import com.bike.bikeproject.service.UserService;
import com.bike.bikeproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User signUp(UserDTO userDTO) {
        User userToSave = User.builder()
                .userId(userDTO.getUserId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .name(userDTO.getUsername())
                .role(Role.ROLE_USER)
                .build();
        log.info("USER 회원가입 처리: {}", userToSave.toString());
        return userRepository.save(userToSave);
    }

    @Override
    public JwtDTO authenticate(UserDTO userDTO) {
        // authenticate 메소드가 내부적으로 인증 과정을 거친다 (패스워드 체크)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUserId(), userDTO.getPassword()
                )
        );
        User user = userRepository.findByUserId(userDTO.getUserId())
                .orElseThrow(() -> new JwtAuthException("User with ID " + userDTO.getUserId() + " is not present"));
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new JwtDTO(accessToken, refreshToken);
    }

}
