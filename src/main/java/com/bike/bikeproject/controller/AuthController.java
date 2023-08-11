package com.bike.bikeproject.controller;

import com.bike.bikeproject.dto.UserDTO;
import com.bike.bikeproject.entity.User;
import com.bike.bikeproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "AUTH", description = "AUTHENTICATION 용 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(tags = "AUTH",
            summary = "User Sign-Up", description = "유저 회원가입 API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestBody @Valid UserDTO userDTO
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().body("Invalid User ID or Password"); }
        User user = userService.signUp(userDTO);
        return ResponseEntity.ok(user.toString());
    }

}
