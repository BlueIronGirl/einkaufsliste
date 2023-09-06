package de.shoppinglist.controller;

import de.shoppinglist.entity.Kategorie;
import de.shoppinglist.service.KategorieService;
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
    @GetMapping
    public List<Kategorie> selectAllKategorien() {
        return kategorieService.selectAllKategorien();
    }
}
