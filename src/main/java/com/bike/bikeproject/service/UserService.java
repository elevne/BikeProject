package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.User;

public interface UserService {

    User signUp(UserDTO userDTO);

}
