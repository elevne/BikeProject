package com.bike.bikeproject.controller;

import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().body("부적절한 유저정보가 넘어옴"); }
        User user = userService.signUp(userDTO);
        return ResponseEntity.ok(user.toString());
    }

}
