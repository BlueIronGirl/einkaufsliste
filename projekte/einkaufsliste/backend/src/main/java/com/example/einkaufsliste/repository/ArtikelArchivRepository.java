package com.example.einkaufsliste.repository;

import com.example.einkaufsliste.entity.ArtikelArchiv;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtikelArchivRepository extends CrudRepository<ArtikelArchiv, Long> {
    @Override
    List<ArtikelArchiv> findAll();
}
