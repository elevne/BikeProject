package com.bike.bikeproject.entity;

import lombok.*;

import javax.naming.spi.NamingManager;
import javax.persistence.*;
import java.time.Instant;

// todo: 캐시화 시킬 수 있는지 찾아보기
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true)
    private String token;

    // todo: Date 클래스와 비교 / 찾아보기
    private Instant expiryDate;

    private String userId;
}
