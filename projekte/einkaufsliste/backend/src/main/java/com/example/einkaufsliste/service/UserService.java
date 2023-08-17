package com.example.einkaufsliste.service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Set;

import com.example.einkaufsliste.dto.LoginDto;
import com.example.einkaufsliste.dto.RegisterDto;
import com.example.einkaufsliste.entity.User;
import com.example.einkaufsliste.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    public User login(LoginDto loginDto) {
        User user = findByLogin(loginDto.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Passwort falsch!");
    }

    public User register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByUsername(registerDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("Benutzer existiert bereits");
        }

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb, violations);
        }


        User user = User.builder()
            .username(registerDto.getUsername())
            .password(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())))
            .build();

        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("Unbekannter User!"));
    }
}
