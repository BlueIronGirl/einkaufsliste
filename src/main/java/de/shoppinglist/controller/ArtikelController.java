package de.shoppinglist.controller;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.service.ArtikelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller-Class providing the REST-Endpoints for the Artikel-Entity
 */
@RestController
@RequestMapping("artikels")
public class ArtikelController {
    private final ArtikelService artikelService;

    @Autowired
    public ArtikelController(ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @Operation(summary = "Get all articles", description = "Get all articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Artikel.class)))
            })
    })
    @GetMapping
    public ResponseEntity<List<Artikel>> selectAllArtikel() {
        return ResponseEntity.ok(artikelService.selectAllArtikel());
    }

    @Operation(summary = "Get one article", description = "Get one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Artikel> selectArtikel(@PathVariable Long id) {
        return ResponseEntity.ok(artikelService.selectArtikel(id));
    }

    @Operation(summary = "Create new article", description = "Create new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article")
    })
    @PostMapping
    public ResponseEntity<Artikel> createArtikel(@Valid @RequestBody Artikel artikel) {
        return ResponseEntity.ok(artikelService.createArtikel(artikel));
    }

    @Operation(summary = "Update one article", description = "Update one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article"),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<Artikel> updateArtikel(@Valid @RequestBody Artikel artikelData, @PathVariable Long id) {
        return ResponseEntity.ok(artikelService.updateArtikel(artikelData, id));
    }

    @Operation(summary = "Delete one article", description = "Delete one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Artikel> deleteArtikel(@PathVariable Long id) {
        return ResponseEntity.ok(artikelService.deleteArtikel(id));
    }
}
