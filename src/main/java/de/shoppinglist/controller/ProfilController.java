package de.shoppinglist.controller;

import de.shoppinglist.dto.ModelMapperDTO;
import de.shoppinglist.dto.UserDTO;
import de.shoppinglist.entity.User;
import de.shoppinglist.service.ProfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profil")
@PreAuthorize("hasRole('USER')")
public class ProfilController {

    private final ProfilService profilService;
    private final ModelMapperDTO modelMapper;

    @Autowired
    public ProfilController(ProfilService profilService, ModelMapperDTO modelMapper) {
        this.profilService = profilService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "upload profile image", description = "upload profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema()))
            })
    })
    @PostMapping
    public ResponseEntity<UserDTO> saveUserProfile(@RequestBody UserDTO user) {
        User userDB = this.profilService.saveUserProfile(user);
        return ResponseEntity.ok(this.modelMapper.getModelMapper().map(userDB, UserDTO.class));
    }
}
