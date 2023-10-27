package de.shoppinglist.service;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.repository.ArtikelRepository;
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

    public Artikel selectArtikel(@PathVariable Long id) {
        return artikelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Artikel nicht gefunden"));
    }

    public Artikel createArtikel(@RequestBody Artikel artikelData) {
        artikelData.setErstellungsZeitpunkt(LocalDateTime.now());
        return artikelRepository.save(artikelData);
    }

    public Artikel updateArtikel(@RequestBody Artikel artikelData, @PathVariable Long id) {
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
                .orElseThrow(() -> new EntityNotFoundException("Artikel nicht gefunden"));
    }

    public Artikel deleteArtikel(@PathVariable Long id) {
        Artikel artikel = artikelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artikel nicht gefunden"));

        artikelRepository.deleteById(id);

        return artikel;
    }
}
