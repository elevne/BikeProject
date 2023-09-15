package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.dto.JwtDTO;
import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.exception.AuthenticationException;
import com.bike.bikeproject.repository.UserRepository;
import com.bike.bikeproject.service.UserService;
import com.bike.bikeproject.util.JwtUtil;
import com.bike.bikeproject.util.impl.JwtUtilImpl;
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
                .username(userDTO.getUserId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .username(userDTO.getUsername())
                .role(Role.ROLE_USER)
                .build();
        log.info("USER 회원가입 처리: {}", userToSave.toString());
        return userRepository.save(userToSave);
    }

    // todo: 이 파트 다시 확인해보기
    @Override
    public JwtDTO authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUserId(), userDTO.getPassword()
                )
        );
        User user = userRepository.findByUserId(userDTO.getUserId())
                .orElseThrow(() -> new AuthenticationException("User with ID " + userDTO.getUserId() + " is not present"));
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new JwtDTO(accessToken, refreshToken);
    }

    // todo: Token Refresh 어떻게 할 건지 고민해보기

}
