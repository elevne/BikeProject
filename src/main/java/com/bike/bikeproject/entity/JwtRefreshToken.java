package com.bike.bikeproject.entity;

import lombok.*;

import javax.naming.spi.NamingManager;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true)
    private String token;

    private Instant expiryDate;

    private String userId;
}
