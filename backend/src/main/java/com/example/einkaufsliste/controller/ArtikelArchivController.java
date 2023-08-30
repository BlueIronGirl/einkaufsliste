package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.entity.Artikel;
import com.example.einkaufsliste.entity.ArtikelArchiv;
import com.example.einkaufsliste.service.ArtikelArchivService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ArtikelArchivController {
    private final ArtikelArchivService artikelArchivService;

    /**
     * Alle Artikel von dem Archiv abfragen
     *
     * @return alle Artikel als Liste
     */
    @GetMapping("/archiv/artikels")
    public List<ArtikelArchiv> selectAllArtikelArchiv() {
        return artikelArchivService.selectAllArtikelArchiv();
    }

    /**
     * Alle gekauften Artikel archivieren
     *
     * @return archivierte Artikel
     */
    @PostMapping("/archiv/archiviereGekaufteArtikel")
    public List<Artikel> archiviereGekaufteArtikel() {
        return artikelArchivService.loescheArchivierteArtikel();
    }
}
