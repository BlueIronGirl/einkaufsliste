package com.example.einkaufsliste;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/alleGekauftenArtikel")
//    public List<Artikel> alleGekauftenArtikel() {
//        return artikelRepository.findeAlleGekauftenArtikel();
//    }
//
//    @GetMapping("/alleNichtGekauftenArtikel")
//    public List<Artikel> alleNichtGekauftenArtikel() {
//        return artikelRepository.findeAlleNichtGekauftenArtikel();
//    }

    @GetMapping("/artikel/{id}")
    public Artikel ladeArtikel(@PathVariable Long id) throws Exception {
        return artikelRepository.findById(id).orElseThrow(() -> new Exception("Artikel nicht gefunden"));
    }

    @PostMapping(value = "/artikel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artikel neuerArtikel(@RequestBody Artikel artikel) {
        return artikelRepository.save(artikel);
    }

    @PutMapping("/artikel/{id}")
    public Artikel ersetzeArtikel(@RequestBody Artikel newArtikel, @PathVariable Long id) {
        return artikelRepository.findById(id)
                .map(artikel -> {
                    artikel.setName(newArtikel.getName());
                    artikel.setAnzahl(newArtikel.getAnzahl());
                    artikel.setGekauft(newArtikel.isGekauft());
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
