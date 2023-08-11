package com.example.einkaufsliste.service;

import com.example.einkaufsliste.entity.ArtikelArchiv;
import com.example.einkaufsliste.repository.ArtikelArchivRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ArtikelArchivService {
    private final ArtikelArchivRepository artikelArchivRepository;

    public List<ArtikelArchiv> selectAllArtikelArchiv() {
        return artikelArchivRepository.findAll();
    }
}
