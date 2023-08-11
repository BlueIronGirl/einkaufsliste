package com.example.einkaufsliste.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity(name="artikelarchiv")
@AllArgsConstructor
public class ArtikelArchiv extends Artikel{
    public ArtikelArchiv(Artikel artikel) {
        super(artikel);
    }
}
