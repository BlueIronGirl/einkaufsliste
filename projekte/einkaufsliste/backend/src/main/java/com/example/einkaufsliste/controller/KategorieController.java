package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.entity.Kategorie;
import com.example.einkaufsliste.repository.KategorieRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class KategorieController {
    KategorieRepository kategorieRepository;

    public KategorieController(KategorieRepository kategorieRepository) {
        this.kategorieRepository = kategorieRepository;
    }

    @GetMapping("/alleKategorien")
    public List<Kategorie> allKategorien() {
        return kategorieRepository.findAll();
    }
}
