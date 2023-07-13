package com.example.einkaufsliste;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtikelRepository extends CrudRepository<Artikel, Long> {
    @Override
    List<Artikel> findAll();

    @Query("SELECT a FROM Artikel a WHERE a.gekauft = true")
    List<Artikel> findeAlleGekauftenArtikel();

    @Query("SELECT a FROM Artikel a WHERE a.gekauft = false")
    List<Artikel> findeAlleNichtGekauftenArtikel();
}
