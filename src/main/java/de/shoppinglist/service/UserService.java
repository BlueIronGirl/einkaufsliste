package de.shoppinglist.service;

import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;

/**
 * Service-Class providing the business logic for the User-Entity
 * <p>
 * The User-Table is used to store the users of the application
 * <p>
 * Users can be registered and logged in
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User nicht gefunden"));
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User nicht gefunden");
        }
    }

    public User update(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDetails.getUsername());
                    if (StringUtils.isNotEmpty(userDetails.getPassword())) {
                        existingUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDetails.getPassword())));
                    }
                    existingUser.setName(userDetails.getName());
                    existingUser.setEmail(userDetails.getEmail());
                    existingUser.setRoles(userDetails.getRoles());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("User nicht gefunden"));
    }
}
