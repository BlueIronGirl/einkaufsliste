package de.shoppinglist.repository;

import de.shoppinglist.entity.Artikel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtikelRepository extends JpaRepository<Artikel, Long> {
    List<Artikel> findByGekauftTrue();
}
