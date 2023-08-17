package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.repository.UserRepository;
import com.bike.bikeproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(UserDTO userDTO) {
        User userToSave = User.builder()
                .username(userDTO.getUserId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        log.info("USER 회원가입 처리: {}", userToSave.toString());
        return userRepository.save(userToSave);
    }

    // todo: AUTH 관련 작업 이어 작성하기
}
