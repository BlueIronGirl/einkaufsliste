package de.shoppinglist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.shoppinglist.entity.base.EntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

/**
 * Entity-Class representing the User-Table
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class User extends EntityBase {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Transient
    private String token;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    @JsonIgnore
    private List<Einkaufszettel> einkaufszettels;

    @Builder
    public User(Long id, String username, String password, String name, String token) {
        super(id);
        this.username = username;
        this.password = password;
        this.name = name;
        this.token = token;
    }
}
