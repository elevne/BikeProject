package com.bike.bikeproject.dto;

import com.bike.bikeproject.entity.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    @Size(min = 5, max = 20)
    @NotEmpty(message = "ID is empty")
    private String userId;

    @Size(min = 8, max = 20)
    @NotEmpty(message = "Password is empty")
    private String password;

}
