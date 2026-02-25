package com.amdox.taskmanagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdox.taskmanagement.dto.AuthResponse;
import com.amdox.taskmanagement.dto.LoginRequest;
import com.amdox.taskmanagement.dto.RegisterRequest;
import com.amdox.taskmanagement.model.User;
import com.amdox.taskmanagement.security.JwtService;
import com.amdox.taskmanagement.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    public AuthController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
}

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                "USER"
        );
        return userService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        User user = userService.login(request);
        String token = jwtService.generateToken(
            user.getEmail(),
            user.getRole()
    );

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(), 
                token
        );
    }
}