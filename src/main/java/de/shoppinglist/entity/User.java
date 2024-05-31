package de.shoppinglist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.shoppinglist.entity.base.EntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;

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

    @NotBlank
    @Email
    private String email;

    @Transient
    private String token;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    @JsonIgnore
    private List<Einkaufszettel> einkaufszettels;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Builder
    public User(Long id, String username, String password, String name, String token) {
        super(id);
        this.username = username;
        this.password = password;
        this.name = name;
        this.token = token;
    }
}
