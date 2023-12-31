package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    Optional<User> findByUserId(String userId);
}
