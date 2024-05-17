package de.shoppinglist.entity;

import de.shoppinglist.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Einkaufszettel extends EntityBase {
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "einkaufszettel")
    @ToString.Exclude
    private List<Artikel> artikels;

    @ManyToMany
    @JoinTable(
            name = "einkaufszettel_user",
            joinColumns = @JoinColumn(name = "einkaufszettel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private List<User> users;

    @Builder
    public Einkaufszettel(Long id, String name, List<Artikel> artikels) {
        super(id);
        this.name = name;
        this.artikels = artikels;
    }
}
