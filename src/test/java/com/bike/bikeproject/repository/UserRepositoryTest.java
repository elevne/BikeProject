package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @PersistenceContext EntityManager em;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("elevne")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("User 저장, 검색 테스트")
    public void testFindByUsername() {
        // given: @BeforeEach User 을 저장
        userRepository.save(user);
        // when: 해당 유저를 findByUsername 메소드로 조회
        User user = userRepository.findByUsername("elevne")
                .orElseThrow(() -> new UsernameNotFoundException("User with name 'elevne' is not found"));
        // then:
        assertEquals("elevne", user.getUsername());
        assertEquals("1234", user.getPassword());
        assertEquals(Role.ROLE_USER, user.getRole());
    }

    // todo: username, userId 따로 만들기 -> 지금은 username (unique x) 만 있음 / unique constraint 의 user id 추가하기
    @Test
    @DisplayName("User ID 중복 저장 예외 테스트")
    public void testUserDuplicateID() {
        // given
        User user2 = User.builder()
                        .username("elevne")
                        .password("5678")
                        .role(Role.ROLE_USER)
                        .build();
        userRepository.save(user);
        // when, then
        userRepository.save(user2);
    }
}
