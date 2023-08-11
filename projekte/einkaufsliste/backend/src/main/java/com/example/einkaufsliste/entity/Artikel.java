package com.example.einkaufsliste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="artikel")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Artikel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    @JsonIgnore
    private Kategorie kategorie;

    private int anzahl;

    private boolean gekauft;

    private LocalDateTime erstellungsZeitpunkt;

    private LocalDateTime kaufZeitpunkt;

    public Artikel(Artikel artikel) {
        this(artikel.id, artikel.name, artikel.kategorie, artikel.anzahl, artikel.gekauft, artikel.erstellungsZeitpunkt, artikel.kaufZeitpunkt);
    }

    // TODO (ALB) 27.07.2023: erstelleruserid, käuferuserid
}
