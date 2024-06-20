package de.shoppinglist.service;

import de.shoppinglist.entity.ArtikelArchiv;
import de.shoppinglist.entity.User;
import de.shoppinglist.repository.ArtikelArchivRepository;
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
    private final ArtikelArchivRepository artikelArchivRepository;
    private final AuthService authService;

    @Autowired
    public ArtikelArchivService(ArtikelArchivRepository artikelArchivRepository, AuthService authService) {
        this.artikelArchivRepository = artikelArchivRepository;
        this.authService = authService;
    }

    public List<ArtikelArchiv> findByUserId() {
        User currentUser = authService.findCurrentUser();

        return artikelArchivRepository.findByEinkaufszettel_Owners_IdOrEinkaufszettel_SharedWith_IdOrderByKaufZeitpunktDesc(currentUser, currentUser);
    }
}
