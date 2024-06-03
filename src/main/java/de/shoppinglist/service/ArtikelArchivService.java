package de.shoppinglist.service;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.ArtikelArchiv;
import de.shoppinglist.repository.ArtikelArchivRepository;
import de.shoppinglist.repository.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service-Class providing the business logic for the ArtikelArchiv-Entity
 * <p>
 * The ArtikelArchiv-Table is used to display the bought articles in the history
 * <p>
 * All Articles that have been bought are archived in the ArtikelArchiv-Table
 * and deleted from the Artikel-Table
 * <p>
 */
@Service
public class ArtikelArchivService {
    private final ArtikelRepository artikelRepository;
    private final ArtikelArchivRepository artikelArchivRepository;
    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public ArtikelArchivService(ArtikelRepository artikelRepository, ArtikelArchivRepository artikelArchivRepository, UserAuthenticationService userAuthenticationService) {
        this.artikelRepository = artikelRepository;
        this.artikelArchivRepository = artikelArchivRepository;
        this.userAuthenticationService = userAuthenticationService;
    }

    public List<ArtikelArchiv> findByUserId() {
        Long userId = userAuthenticationService.findCurrentUser().getId();

        return artikelArchivRepository.findByEinkaufszettel_Owners_IdOrEinkaufszettel_SharedWith_IdOrderByKaufZeitpunktDesc(userId, userId);
    }

    public void archiviereGekaufteArtikel() {
        Long userId = userAuthenticationService.findCurrentUser().getId();

        List<Artikel> gekaufteArtikel = artikelRepository.findByGekauftTrueAndEinkaufszettel_Owners_IdOrEinkaufszettel_SharedWith_Id(userId, userId);

        // Artikel archivieren
        gekaufteArtikel.stream().map(ArtikelArchiv::new).forEach(artikelArchivRepository::saveAndFlush);

        // Artikel aus Einkaufszettel loeschen
        gekaufteArtikel.forEach(artikel -> artikelRepository.deleteById(artikel.getId()));
    }
}
