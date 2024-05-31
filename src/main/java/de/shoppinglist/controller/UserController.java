package de.shoppinglist.controller;

import de.shoppinglist.entity.User;
import de.shoppinglist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<User> selectAllUsersExceptMe() {
        Long userId = userService.findCurrentUser().getId();
        return userService.findAll().stream().filter(userDB -> !userDB.getId().equals(userId)).toList();
    }

//    @Operation(summary = "Get all users", description = "Get all users")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Ok", content = {
//                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
//            })
//    })
//    @GetMapping
//    public List<User> selectAllUsers() {
//        return userService.selectAllUsers();
//    }
}
