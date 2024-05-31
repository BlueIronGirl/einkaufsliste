package de.shoppinglist.controller;

import de.shoppinglist.entity.RoleName;
import de.shoppinglist.entity.User;
import de.shoppinglist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller-Class providing the REST-Endpoints for the Kategorie-Entity
 */
@RestController
@RequestMapping("user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users except me", description = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
            })
    })
    @GetMapping("/friends")
    @PreAuthorize("hasRole('USER')")
    public List<User> selectAllFriends() {
        Long userId = userService.findCurrentUser().getId();
        return userService.findAll().stream()
                .filter(userDB -> !userDB.getId().equals(userId))
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN) || role.getName().equals(RoleName.ROLE_USER)))
                .toList();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.status(201).body(createdUser);
    }
}
