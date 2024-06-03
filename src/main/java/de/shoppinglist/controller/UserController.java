package de.shoppinglist.controller;

import de.shoppinglist.dto.RegisterDto;
import de.shoppinglist.entity.RoleName;
import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.service.UserAuthenticationService;
import de.shoppinglist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller-Class providing the REST-Endpoints for the User-Entity
 */
@RestController
@RequestMapping("user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public UserController(UserService userService, UserAuthenticationService userAuthenticationService) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
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
        return userService.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN) || role.getName().equals(RoleName.ROLE_USER)))
                .toList();
    }

    @Operation(summary = "Get all user", description = "Get all user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
            })
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Create new user", description = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user")
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody RegisterDto user) {
        User createdUser = userAuthenticationService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Update one user", description = "Update one user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user"),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.update(id, userDetails));
    }

    @Operation(summary = "Delete one user", description = "Delete one user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
