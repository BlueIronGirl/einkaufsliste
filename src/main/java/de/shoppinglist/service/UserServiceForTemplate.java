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
import java.util.List;
import java.util.Optional;

/**
 * Service-Class providing the business logic for the User-Entity
 * <p>
 * The User-Table is used to store the users of the application
 */
@Service
public class UserServiceForTemplate {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceForTemplate(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User nicht gefunden"));
    }

    public User save(User user) {
        return userRepository.save(user);
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
                    existingUser.setName(userDetails.getName());
                    existingUser.setEmail(userDetails.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("User nicht gefunden"));
    }
}
