package de.shoppinglist.controller;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.Einkaufszettel;
import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.service.EinkaufszettelService;
import de.shoppinglist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/einkaufszettel")
public class EinkaufszettelController {
    private final EinkaufszettelService einkaufszettelService;
    private final UserService userService;

    public EinkaufszettelController(EinkaufszettelService einkaufszettelService, UserService userService) {
        this.einkaufszettelService = einkaufszettelService;
        this.userService = userService;
    }

    @Operation(summary = "Get all einkaufszettels", description = "Get all einkaufszettels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Einkaufszettel.class)))
            })
    })
    @GetMapping
    public ResponseEntity<List<Einkaufszettel>> selectAllEinkaufszettels() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        Long userId = userService.findByLogin(username).getId();

        return ResponseEntity.ok(einkaufszettelService.findByUserId(userId));
    }

    @Operation(summary = "Create new einkaufszettel", description = "Create new einkaufszettel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Einkaufszettel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid einkaufszettel")
    })
    @PostMapping
    public Einkaufszettel createEinkaufszettel(@RequestBody Einkaufszettel einkaufszettel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        User userDB = userService.findByLogin(username);

        List<User> users = einkaufszettel.getUsers();
        users.add(userDB);
        einkaufszettel.setUsers(users);

        return einkaufszettelService.saveEinkaufszettel(einkaufszettel);
    }

    @Operation(summary = "Update one Einkaufszettel", description = "Update one Einkaufszettel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Einkaufszettel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Einkaufszettel"),
            @ApiResponse(responseCode = "404", description = "Einkaufszettel not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<Einkaufszettel> updateEinkaufszettel(@Valid @RequestBody Einkaufszettel einkaufszettel, @PathVariable Long id) {
        return ResponseEntity.ok(einkaufszettelService.updateEinkaufszettel(einkaufszettel, id));
    }

    @Operation(summary = "Delete one Einkaufszettel", description = "Delete one Einkaufszettel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Einkaufszettel.class))}),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEinkaufszettel(@PathVariable Long id) {
        einkaufszettelService.deleteEinkaufszettel(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new article", description = "Create new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article")
    })
    @PostMapping("/{id}/artikel")
    public ResponseEntity<Artikel> createArtikel(@PathVariable(name = "id") Long einkaufszettelId, @Valid @RequestBody Artikel artikel) {
        return ResponseEntity.ok(einkaufszettelService.createArtikel(einkaufszettelId, artikel));
    }

    @Operation(summary = "Update one article", description = "Update one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article"),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @PutMapping("/artikel/{id}")
    public ResponseEntity<Artikel> updateArtikel(@Valid @RequestBody Artikel artikelData, @PathVariable Long id) {
        return ResponseEntity.ok(einkaufszettelService.updateArtikel(artikelData, id));
    }

    @Operation(summary = "Delete one article", description = "Delete one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @DeleteMapping("/artikel/{id}")
    public ResponseEntity<Void> deleteArtikel(@PathVariable Long id) {
        einkaufszettelService.deleteArtikel(id);
        return ResponseEntity.noContent().build();
    }

}
