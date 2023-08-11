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
    @GetMapping("/selectAllArtikel")
    public List<Artikel> selectAllArtikel() {
        return artikelService.alleArtikel();
    }

    /**
     * Lade einen bestimmten Artikel mit der Id
     *
     * @param id id
     * @return Artikel
     * @throws Exception wenn der Artikel nicht gefunden wurde
     */
    @GetMapping("/artikel/{id}")
    public Artikel selectArtikel(@PathVariable Long id) throws Exception {
        return artikelService.selectAllArtikel(id);
    }

    /**
     * Neuen Artikel erfassen. Dabei wird der Erstellungszeitpunkt auf den aktuellen Zeitpunkt gesetzt
     *
     * @param artikel zu speichernder Artikel
     * @return erfasster Artikel
     */
    @PostMapping(value = "/artikel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PutMapping("/artikel/{id}")
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
    @DeleteMapping("/artikel/{id}")
    public Artikel deleteArtikel(@PathVariable Long id) throws Exception {
        return artikelService.deleteArtikel(id);
    }

    /**
     * Alle gekauften Artikel archivieren
     *
     * @return archivierte Artikel
     */
    @PostMapping("/archiviereGekaufteArtikel")
    public List<Artikel> archiviereGekaufteArtikel() {
        return artikelService.archiviereGekaufteArtikel();
    }
}
