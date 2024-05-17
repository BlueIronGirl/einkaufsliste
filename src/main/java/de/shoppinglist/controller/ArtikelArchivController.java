package de.shoppinglist.controller;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.ArtikelArchiv;
import de.shoppinglist.entity.User;
import de.shoppinglist.service.ArtikelArchivService;
import de.shoppinglist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller-Class providing the REST-Endpoints for the ArtikelArchiv-Entity
 */
@RestController
@RequestMapping("archiv")
public class ArtikelArchivController {
    private final ArtikelArchivService artikelArchivService;
    private final UserService userService;

    @Autowired
    public ArtikelArchivController(ArtikelArchivService artikelArchivService, UserService userService) {
        this.artikelArchivService = artikelArchivService;
        this.userService = userService;
    }

    @Operation(summary = "Get all archived articles", description = "Get all archived articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ArtikelArchiv.class)))
            })
    })
    @GetMapping
    public List<ArtikelArchiv> selectAllArtikelArchiv() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        Long userId = userService.findByLogin(username).getId();

        return artikelArchivService.findByUserId(userId);
    }

    @Operation(summary = "Get all archived articles", description = "Get all archived articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Artikel.class)))
            })
    })
    @PostMapping("/archiviereGekaufteArtikel")
    public ResponseEntity<Void> archiviereGekaufteArtikel() {
        artikelArchivService.archiviereGekaufteArtikel();
        return ResponseEntity.noContent().build();
    }
}
