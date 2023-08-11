package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.entity.Artikel;
import com.example.einkaufsliste.service.ArtikelService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
public class ArtikelController {
    private final ArtikelService artikelService;

    /**
     * Alle Artikel von dem aktiven Einkaufszettel abfragen
     *
     * @return alle Artikel als Liste
     */
    @GetMapping("/artikels")
    public List<Artikel> selectAllArtikel() {
        return artikelService.selectAllArtikel();
    }

    /**
     * Lade einen bestimmten Artikel mit der Id
     *
     * @param id id
     * @return Artikel
     * @throws Exception wenn der Artikel nicht gefunden wurde
     */
    @GetMapping("/artikels/{id}")
    public Artikel selectArtikel(@PathVariable Long id) throws Exception {
        return artikelService.selectArtikel(id);
    }

    /**
     * Neuen Artikel erfassen. Dabei wird der Erstellungszeitpunkt auf den aktuellen Zeitpunkt gesetzt
     *
     * @param artikel zu speichernder Artikel
     * @return erfasster Artikel
     */
    @PostMapping(value = "/artikels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artikel createArtikel(@RequestBody Artikel artikel) {
        return artikelService.createArtikel(artikel);
    }

    /**
     * Bestehenden Artikel aktualisieren. Wenn dabei der Artikel auf gekauft gesetzt wird, wird der Kaufzeitpunkt auf den aktuellen Zeitpunkt gesetzt
     *
     * @param artikelData zu speichernder Artikel
     * @param id          ID des Artikels
     * @return aktualisierter Artikel
     * @throws Exception Artikel wurde nicht gefunden
     */
    @PutMapping("/artikels/{id}")
    public Artikel updateArtikel(@RequestBody Artikel artikelData, @PathVariable Long id) throws Exception {
        return artikelService.updateArtikel(artikelData, id);
    }

    /**
     * Loesche bestehenden Artikel
     *
     * @param id ID des Artikels
     * @return geloeschter Artikel
     * @throws Exception Artikel wurde nicht gefunden
     */
    @DeleteMapping("/artikels/{id}")
    public Artikel deleteArtikel(@PathVariable Long id) throws Exception {
        return artikelService.deleteArtikel(id);
    }
}
