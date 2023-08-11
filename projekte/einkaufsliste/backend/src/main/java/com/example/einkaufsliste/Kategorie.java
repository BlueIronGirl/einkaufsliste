package com.example.einkaufsliste;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Kategorie {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "kategorie", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Artikel> artikels;
}
