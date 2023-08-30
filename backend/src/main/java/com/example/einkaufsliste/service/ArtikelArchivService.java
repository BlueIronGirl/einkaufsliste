package com.example.einkaufsliste.service;

import com.example.einkaufsliste.entity.Artikel;
import com.example.einkaufsliste.entity.ArtikelArchiv;
import com.example.einkaufsliste.repository.ArtikelArchivRepository;
import com.example.einkaufsliste.repository.ArtikelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ArtikelArchivService {
    private final ArtikelRepository artikelRepository;
    private final ArtikelArchivRepository artikelArchivRepository;

    public List<ArtikelArchiv> selectAllArtikelArchiv() {
        return artikelArchivRepository.findAll();
    }

    /**
     * Alle gekauften Artikel archivieren
     *
     * @return archivierte Artikel
     */
    public List<Artikel> loescheArchivierteArtikel() {
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
