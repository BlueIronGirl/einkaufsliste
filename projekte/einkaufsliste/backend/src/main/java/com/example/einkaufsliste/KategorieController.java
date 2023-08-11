package com.example.einkaufsliste;

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
