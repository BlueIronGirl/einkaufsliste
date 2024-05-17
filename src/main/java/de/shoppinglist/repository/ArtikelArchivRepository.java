package de.shoppinglist.repository;

import de.shoppinglist.entity.ArtikelArchiv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for the ArtikelArchiv-Entity
 */
public interface ArtikelArchivRepository extends JpaRepository<ArtikelArchiv, Long> {
    List<ArtikelArchiv> findByEinkaufszettelUsersIdOrderByKaufZeitpunktDesc(Long userId);

}
