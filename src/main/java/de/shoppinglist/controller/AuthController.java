package de.shoppinglist.controller;

import de.shoppinglist.dto.LoginDto;
import de.shoppinglist.dto.RegisterDto;
import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityAlreadyExistsException;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.exception.UnautorizedException;
import de.shoppinglist.service.UserAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Controller-Class providing the REST-Endpoints for the Login and Register of the Users of the Application
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public AuthController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Operation(summary = "Login to the Application and get a valid token", description = "Login to the Application and get a valid token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid user"),
            @ApiResponse(responseCode = "401", description = "Password is not correct",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnautorizedException.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userAuthenticationService.login(loginDto);
        user.setToken(userAuthenticationService.createToken(user));

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Register to the Application and get a valid token", description = "Register to the Application and get a valid token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid user"),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityAlreadyExistsException.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDto registerDto) {
        User createdUser = userAuthenticationService.register(registerDto);
        createdUser.setToken(userAuthenticationService.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
