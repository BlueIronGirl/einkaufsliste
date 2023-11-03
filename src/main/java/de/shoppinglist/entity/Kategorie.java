package de.shoppinglist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * Entity-Class representing the Kategorie-Table
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Kategorie {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "kategorie", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Artikel> artikels;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kategorie kategorie = (Kategorie) o;
        return Objects.equals(id, kategorie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
