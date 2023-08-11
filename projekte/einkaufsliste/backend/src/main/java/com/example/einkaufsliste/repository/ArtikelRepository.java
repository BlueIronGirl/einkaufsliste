package com.example.einkaufsliste.repository;

import com.example.einkaufsliste.entity.Artikel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtikelRepository extends CrudRepository<Artikel, Long> {
    List<Artikel> findByGekauftTrue();

    @Override
    List<Artikel> findAll();
}
