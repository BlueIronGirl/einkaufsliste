package com.example.einkaufsliste.service;

import com.example.einkaufsliste.entity.Artikel;
import com.example.einkaufsliste.entity.ArtikelArchiv;
import com.example.einkaufsliste.repository.ArtikelArchivRepository;
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
    private final ArtikelArchivRepository artikelArchivRepository;

    /**
     * Alle Artikel von dem aktiven Einkaufszettel abfragen
     *
     * @return alle Artikel als Liste
     */
    public List<Artikel> alleArtikel() {
        return artikelRepository.findAll();
    }

    /**
     * Lade einen bestimmten Artikel mit der Id
     *
     * @param id id
     * @return Artikel
     * @throws Exception wenn der Artikel nicht gefunden wurde
     */
    public Artikel selectAllArtikel(@PathVariable Long id) throws Exception {
        return artikelRepository.findById(id).orElseThrow(() -> new Exception("Artikel nicht gefunden"));
    }

    /**
     * Neuen Artikel erfassen. Dabei wird der Erstellungszeitpunkt auf den aktuellen Zeitpunkt gesetzt
     *
     * @param artikelData zu speichernder Artikel
     * @return erfasster Artikel
     */
    public Artikel createArtikel(@RequestBody Artikel artikelData) {
        artikelData.setErstellungsZeitpunkt(LocalDateTime.now());
        return artikelRepository.save(artikelData);
    }

    /**
     * Bestehenden Artikel aktualisieren. Wenn dabei der Artikel auf gekauft gesetzt wird, wird der Kaufzeitpunkt auf den aktuellen Zeitpunkt gesetzt
     *
     * @param artikelData zu speichernder Artikel
     * @param id          ID des Artikels
     * @return aktualisierter Artikel
     * @throws Exception Artikel wurde nicht gefunden
     */
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

    /**
     * Loesche bestehenden Artikel
     *
     * @param id ID des Artikels
     * @return geloeschter Artikel
     * @throws Exception Artikel wurde nicht gefunden
     */
    public Artikel deleteArtikel(@PathVariable Long id) throws Exception {
        Artikel artikel = artikelRepository.findById(id)
                .orElseThrow(() -> new Exception("Artikel nicht gefunden"));

        artikelRepository.deleteById(id);

        return artikel;
    }

    /**
     * Alle gekauften Artikel archivieren
     *
     * @return archivierte Artikel
     */
    public List<Artikel> archiviereGekaufteArtikel() {
        List<Artikel> gekaufteArtikel = artikelRepository.findByGekauftTrue();

        // Artikel archivieren
        gekaufteArtikel.stream()
                .map(ArtikelArchiv::new)
                .forEach(artikelArchivRepository::save);

        // Artikel aus Einkaufszettel loeschen
        gekaufteArtikel.forEach(artikel -> artikelRepository.deleteById(artikel.getId()));

        return gekaufteArtikel;
    }
}
