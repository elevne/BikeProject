package com.bike.bikeproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Size;

@Schema(description = "User 로그인 정보 DTO")
@Getter
public class LoginDTO {

    @Size(min = 5, max = 20)
    private String id;

    @Size(min = 8, max = 20)
    private String pwd;

}
