package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.config.UserAuthenticationProvider;
import com.example.einkaufsliste.dto.LoginDto;
import com.example.einkaufsliste.entity.User;
import com.example.einkaufsliste.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        user.setToken(userAuthenticationProvider.createToken(user));

        return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody @Valid LoginDto loginDto) {
        User createdUser = userService.register(loginDto);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
