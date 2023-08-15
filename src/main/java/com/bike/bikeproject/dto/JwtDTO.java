package com.bike.bikeproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String accessToken;
    private String refreshToken;
}
