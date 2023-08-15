package com.example.einkaufsliste.service;

import java.nio.CharBuffer;
import java.util.Optional;

import com.example.einkaufsliste.dto.LoginDto;
import com.example.einkaufsliste.entity.User;
import com.example.einkaufsliste.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(LoginDto loginDto) {
        User user = findByLogin(loginDto.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Passwort falsch!");
    }

    public User register(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByUsername(loginDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("Benutzer existiert bereits");
        }

        User user = User.builder()
            .username(loginDto.getUsername())
            .password(passwordEncoder.encode(CharBuffer.wrap(loginDto.getPassword())))
            .build();

        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("Unbekannter User!"));
    }
}
