package com.bike.bikeproject.entity;

import lombok.*;

import javax.naming.spi.NamingManager;
import javax.persistence.*;
import java.time.Instant;

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

    // todo: String 으로 변경해야하는지 찾아보자
    private Instant expiryDate;

    private String userId;
}
