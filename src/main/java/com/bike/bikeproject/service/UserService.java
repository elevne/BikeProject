package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.JwtDTO;
import com.bike.bikeproject.dto.LoginDTO;
import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.User;

public interface UserService {

    User signUp(UserDTO userDTO);

    JwtDTO authenticate(LoginDTO loginDTO);

}
