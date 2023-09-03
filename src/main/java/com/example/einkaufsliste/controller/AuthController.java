package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.config.UserAuthenticationProvider;
import com.example.einkaufsliste.dto.LoginDto;
import com.example.einkaufsliste.dto.RegisterDto;
import com.example.einkaufsliste.entity.User;
import com.example.einkaufsliste.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@AllArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    /**
     * Login: Dabei wird ein Token fuer die Authentifizierung erstellt
     *
     * @param loginDto LoginDaten
     * @return eingeloggter User mit Token
     */
    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        user.setToken(userAuthenticationProvider.createToken(user));

        return ResponseEntity.ok(user);
    }

    /**
     * Register: Dabei wird ein Token fuer die Authentifizierung erstellt
     *
     * @param registerDto Registrierungsdaten
     * @return eingeloggter User mit Token
     */
    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDto registerDto) {
        User createdUser = userService.register(registerDto);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
