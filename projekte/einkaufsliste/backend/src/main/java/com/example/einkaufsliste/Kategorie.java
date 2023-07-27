package com.example.einkaufsliste;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Kategorie {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
