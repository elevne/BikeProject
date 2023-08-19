package com.bike.bikeproject.dto;

import com.bike.bikeproject.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(description = "User 정보 DTO")
@Data
public class UserDTO {

    @Size(min = 5, max = 20)
    @NotEmpty(message = "ID is empty")
    @Schema(description = "User ID", example = "elevne")
    private String userId;

    @Size(min = 8, max = 20)
    @NotEmpty(message = "Password is empty")
    @Schema(description = "User Password", example = "dlwltm1!")
    private String password;

    @NotEmpty(message = "Username is empty")
    @Schema(description = "User Name", example = "Wonil")
    private String username;

}
