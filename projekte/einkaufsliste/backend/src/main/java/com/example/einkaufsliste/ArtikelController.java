package com.example.einkaufsliste;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class ArtikelController {
    ArtikelRepository artikelRepository;

    public ArtikelController(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @GetMapping("/alleArtikel")
    public List<Artikel> alleArtikel() {
        return artikelRepository.findAll();
    }

    @GetMapping("/artikel/{id}")
    public Artikel ladeArtikel(@PathVariable Long id) throws Exception {
        return artikelRepository.findById(id).orElseThrow(() -> new Exception("Artikel nicht gefunden"));
    }

    @PostMapping(value = "/artikel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artikel neuerArtikel(@RequestBody Artikel artikel) {
        Artikel newArtikel = new Artikel();
        newArtikel.setName(artikel.getName());
        newArtikel.setKategorie(artikel.getKategorie());
        newArtikel.setAnzahl(artikel.getAnzahl());
        newArtikel.setGekauft(artikel.isGekauft());
        newArtikel.setErstellungsZeitpunkt(LocalDateTime.now());
        return artikelRepository.save(newArtikel);
    }

    @PutMapping("/artikel/{id}")
    public Artikel ersetzeArtikel(@RequestBody Artikel newArtikel, @PathVariable Long id) {
        return artikelRepository.findById(id)
                .map(artikel -> {
                    artikel.setName(newArtikel.getName());
                    artikel.setAnzahl(newArtikel.getAnzahl());
                    if (!artikel.isGekauft() && newArtikel.isGekauft()) {
                        artikel.setKaufZeitpunkt(LocalDateTime.now());
                    }
                    artikel.setGekauft(newArtikel.isGekauft());
                    artikel.setKategorie(newArtikel.getKategorie());
                    return artikelRepository.save(artikel);
                })
                .orElseGet(() -> {
                    newArtikel.setId(id);
                    return artikelRepository.save(newArtikel);
                });
    }

    @DeleteMapping("/artikel/{id}")
    public void loescheArtikel(@PathVariable Long id) {
        artikelRepository.deleteById(id);
    }
}
