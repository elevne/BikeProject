package com.bike.bikeproject.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private String token;

    // todo: Date 클래스와 비교 / 찾아보기
    private Instant expiryDate;

    // USER 테이블과 연관관계를 N:M 관계로 맺어주는 것이 좋을까?
    private String userId;
}
