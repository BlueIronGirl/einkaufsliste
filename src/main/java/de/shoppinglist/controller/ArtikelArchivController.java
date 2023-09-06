package de.shoppinglist.controller;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.ArtikelArchiv;
import de.shoppinglist.service.ArtikelArchivService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("archiv")
public class ArtikelArchivController {
    private final ArtikelArchivService artikelArchivService;

    /**
     * Alle Artikel von dem Archiv abfragen
     *
     * @return alle Artikel als Liste
     */
    @GetMapping
    public List<ArtikelArchiv> selectAllArtikelArchiv() {
        return artikelArchivService.selectAllArtikelArchiv();
    }

    /**
     * Alle gekauften Artikel archivieren
     *
     * @return archivierte Artikel
     */
    @PostMapping("/archiviereGekaufteArtikel")
    public List<Artikel> archiviereGekaufteArtikel() {
        return artikelArchivService.archiviereGekaufteArtikel();
    }
}
