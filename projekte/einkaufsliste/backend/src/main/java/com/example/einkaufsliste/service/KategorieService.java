package com.example.einkaufsliste.service;

import com.example.einkaufsliste.entity.Kategorie;
import com.example.einkaufsliste.repository.KategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KategorieService {
    private final KategorieRepository kategorieRepository;

    /**
     * Alle Kategorien laden
     *
     * @return Kategorien als Liste
     */
    public List<Kategorie> selectAllKategorien() {
        return kategorieRepository.findAll();
    }
}
