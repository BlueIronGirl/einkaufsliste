package com.example.einkaufsliste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artikelarchiv")
public class ArtikelArchiv {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    @JsonIgnore
    private Kategorie kategorie;

    private int anzahl;

    private LocalDateTime erstellungsZeitpunkt;

    private LocalDateTime kaufZeitpunkt;

    public ArtikelArchiv(Artikel artikel) {
        this(artikel.getId(), artikel.getName(), artikel.getKategorie(), artikel.getAnzahl(), artikel.getErstellungsZeitpunkt(), artikel.getKaufZeitpunkt());
    }
}
