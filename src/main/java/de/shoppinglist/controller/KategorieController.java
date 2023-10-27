package de.shoppinglist.controller;

import de.shoppinglist.entity.Kategorie;
import de.shoppinglist.service.KategorieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("kategorien")
public class KategorieController {
    private final KategorieService kategorieService;

    /**
     * Alle Kategorien abfragen
     *
     * @return alle Kategorien als Liste
     */
    @Operation(summary = "Get all categories", description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Kategorie.class)))
            })
    })
    @GetMapping
    public List<Kategorie> selectAllKategorien() {
        return kategorieService.selectAllKategorien();
    }
}
