package com.example.einkaufsliste.service;

import com.example.einkaufsliste.entity.Artikel;
import com.example.einkaufsliste.repository.ArtikelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ArtikelService {
    private final ArtikelRepository artikelRepository;

    public List<Artikel> selectAllArtikel() {
        return artikelRepository.findAll();
    }

    public Artikel selectArtikel(@PathVariable Long id) throws Exception {
        return artikelRepository.findById(id).orElseThrow(() -> new Exception("Artikel nicht gefunden"));
    }

    public Artikel createArtikel(@RequestBody Artikel artikelData) {
        artikelData.setErstellungsZeitpunkt(LocalDateTime.now());
        return artikelRepository.save(artikelData);
    }

    public Artikel updateArtikel(@RequestBody Artikel artikelData, @PathVariable Long id) throws Exception {
        return artikelRepository.findById(id)
                .map(artikel -> {
                    artikel.setName(artikelData.getName());
                    artikel.setAnzahl(artikelData.getAnzahl());
                    if (!artikel.isGekauft() && artikelData.isGekauft()) {
                        artikel.setKaufZeitpunkt(LocalDateTime.now());
                    }
                    artikel.setGekauft(artikelData.isGekauft());
                    artikel.setKategorie(artikelData.getKategorie());
                    return artikelRepository.save(artikel);
                })
                .orElseThrow(() -> new Exception("Artikel nicht gefunden"));
    }

    public Artikel deleteArtikel(@PathVariable Long id) throws Exception {
        Artikel artikel = artikelRepository.findById(id)
                .orElseThrow(() -> new Exception("Artikel nicht gefunden"));

        artikelRepository.deleteById(id);

        return artikel;
    }
}
