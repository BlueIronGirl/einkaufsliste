package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.entity.Kategorie;
import com.example.einkaufsliste.service.KategorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
public class KategorieController {
    private final KategorieService kategorieService;

    /**
     * Alle Kategorien abfragen
     *
     * @return alle Kategorien als Liste
     */
    @GetMapping("/kategorien")
    public List<Kategorie> selectAllKategorien() {
        return kategorieService.selectAllKategorien();
    }
}
