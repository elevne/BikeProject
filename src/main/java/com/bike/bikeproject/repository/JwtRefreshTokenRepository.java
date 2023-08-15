package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {

    Optional<JwtRefreshToken> findByToken(String token);

}
