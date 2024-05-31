package de.shoppinglist.service;

import de.shoppinglist.dto.LoginDto;
import de.shoppinglist.dto.RegisterDto;
import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityAlreadyExistsException;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.exception.UnautorizedException;
import de.shoppinglist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;

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

    /**
     * Login a User to the Application and check the user and the password
     *
     * @param loginDto LoginDto containing the username and password
     * @return User-Object of the logged in user
     */
    public User login(LoginDto loginDto) {
        User user = findByLogin(loginDto.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
            return user;
        }
        throw new UnautorizedException("Passwort falsch!");
    }

    /**
     * Register a new User to the Application
     *
     * @param registerDto RegisterDto containing the username, password and name of the new user
     * @return User-Object of the registered user
     */
    public User register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByUsername(registerDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new EntityAlreadyExistsException("Benutzer existiert bereits");
        }

        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())))
                .name(registerDto.getName())
                .build();

        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new EntityNotFoundException("Unbekannter User!"));
    }

    public User findCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Unbekannter User!"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
