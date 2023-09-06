package de.shoppinglist.service;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.ArtikelArchiv;
import de.shoppinglist.repository.ArtikelArchivRepository;
import de.shoppinglist.repository.ArtikelRepository;
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

    public List<Artikel> archiviereGekaufteArtikel() {
        List<Artikel> gekaufteArtikel = artikelRepository.findByGekauftTrue();

        // Artikel archivieren
        gekaufteArtikel.stream()
                .map(ArtikelArchiv::new).forEach(artikelArchivRepository::saveAndFlush);

        // Artikel aus Einkaufszettel loeschen
        gekaufteArtikel.forEach(artikel -> artikelRepository.deleteById(artikel.getId()));

        return gekaufteArtikel;
    }
}
