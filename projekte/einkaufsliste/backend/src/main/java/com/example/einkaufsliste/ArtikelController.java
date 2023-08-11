package com.example.einkaufsliste;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
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
        artikel.setErstellungsZeitpunkt(LocalDateTime.now());
        return artikelRepository.save(artikel);
    }

    @PutMapping("/artikel/{id}")
    public Artikel ersetzeArtikel(@RequestBody Artikel newArtikel, @PathVariable Long id) throws Exception {
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
                .orElseThrow(() -> new Exception("Artikel nicht gefunden"));
    }

    @DeleteMapping("/artikel/{id}")
    public Artikel loescheArtikel(@PathVariable Long id) throws Exception {
        Artikel artikel = artikelRepository.findById(id).orElseThrow(() -> new Exception("Artikel nicht gefunden"));

        artikelRepository.deleteById(id);

        return artikel;
    }
}
