package de.shoppinglist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.shoppinglist.entity.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

/**
 * Entity-Class representing the Kategorie-Table
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Kategorie extends EntityBase {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany
    @ToString.Exclude
    @JsonIgnore
    private List<Artikel> artikels;

    @Builder
    public Kategorie(Long id, Long id1, String name, List<Artikel> artikels) {
        super(id);
        this.id = id1;
        this.name = name;
        this.artikels = artikels;
    }
}
