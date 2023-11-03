package de.shoppinglist.service;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.repository.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service-Class providing the business logic for the Artikel-Entity
 * <p>
 * The Artikel-Table is used to display the articles on the shopping list
 * <p>
 * Articles can be added, updated and deleted
 * The articles can be marked as bought and are then moved to the ArtikelArchiv-Table
 */
@Service
public class ArtikelService {
    private final ArtikelRepository artikelRepository;

    @Autowired
    public ArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

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
