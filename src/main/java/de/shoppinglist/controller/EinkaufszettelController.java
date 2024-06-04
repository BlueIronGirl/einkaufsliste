package de.shoppinglist.controller;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.Einkaufszettel;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.service.EinkaufszettelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/einkaufszettel")
@PreAuthorize("hasRole('GUEST')")
public class EinkaufszettelController {
    private final EinkaufszettelService einkaufszettelService;

    public EinkaufszettelController(EinkaufszettelService einkaufszettelService) {
        this.einkaufszettelService = einkaufszettelService;
    }

    @Operation(summary = "Get all einkaufszettels", description = "Get all einkaufszettels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Einkaufszettel.class)))
            })
    })
    @GetMapping
    public ResponseEntity<List<Einkaufszettel>> selectAllActiveEinkaufszettels() {
        return ResponseEntity.ok(einkaufszettelService.findActiveEinkaufszettelsByUserId());
    }

    @Operation(summary = "Create new einkaufszettel", description = "Create new einkaufszettel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Einkaufszettel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid einkaufszettel")
    })
    @PostMapping
    public Einkaufszettel createEinkaufszettel(@RequestBody Einkaufszettel einkaufszettel) {
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
    @PutMapping("{einkaufszettelId}")
    public ResponseEntity<Einkaufszettel> updateEinkaufszettel(@PathVariable(name = "einkaufszettelId") Long id, @Valid @RequestBody Einkaufszettel einkaufszettel) {
        return ResponseEntity.ok(einkaufszettelService.updateEinkaufszettel(id, einkaufszettel));
    }

    @Operation(summary = "Delete one Einkaufszettel", description = "Delete one Einkaufszettel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Einkaufszettel.class))}),
            @ApiResponse(responseCode = "404", description = "Einkaufszettel not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @DeleteMapping("{einkaufszettelId}")
    public ResponseEntity<Void> deleteEinkaufszettel(@PathVariable(name = "einkaufszettelId") Long id) {
        einkaufszettelService.deleteEinkaufszettel(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new article", description = "Create new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article")
    })
    @PostMapping("/{einkaufszettelId}/artikel")
    public ResponseEntity<Artikel> createArtikel(@PathVariable(name = "einkaufszettelId") Long einkaufszettelId, @Valid @RequestBody Artikel artikel) {
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
    @PutMapping("/{einkaufszettelId}/artikel/{id}")
    public ResponseEntity<Artikel> updateArtikel(@PathVariable(name = "einkaufszettelId") Long einkaufszettelId, @PathVariable(name = "id") Long id, @Valid @RequestBody Artikel artikelData) {
        return ResponseEntity.ok(einkaufszettelService.updateArtikel(einkaufszettelId, id, artikelData));
    }

    @Operation(summary = "Delete one article", description = "Delete one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artikel.class))}),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class))})
    })
    @DeleteMapping("/{einkaufszettelId}/artikel/{id}")
    public ResponseEntity<Void> deleteArtikel(@PathVariable(name = "einkaufszettelId") Long einkaufszettelId, @PathVariable Long id) {
        einkaufszettelService.deleteArtikel(einkaufszettelId, id);
        return ResponseEntity.noContent().build();
    }

}
