package com.example.einkaufsliste;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Artikel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="kategorie_id")
    @JsonIgnore
    private Kategorie kategorie;

    private int anzahl;

    private boolean gekauft;

    private LocalDateTime erstellungsZeitpunkt;

    private LocalDateTime kaufZeitpunkt;

    // TODO (ALB) 27.07.2023: erstelleruserid, käuferuserid
}
