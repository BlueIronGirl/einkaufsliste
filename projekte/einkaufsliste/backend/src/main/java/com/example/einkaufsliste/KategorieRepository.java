package com.example.einkaufsliste;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KategorieRepository extends CrudRepository<Kategorie, Long> {
    Kategorie findByNameIgnoreCase(String name);
    @Override
    List<Kategorie> findAll();
}
