package com.example.einkaufsliste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Cacheable(false)
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
