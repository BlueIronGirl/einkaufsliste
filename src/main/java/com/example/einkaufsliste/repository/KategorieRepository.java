package com.example.einkaufsliste.repository;

import com.example.einkaufsliste.entity.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategorieRepository extends JpaRepository<Kategorie, Long> {

}
