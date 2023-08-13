package com.example.einkaufsliste.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String username;
    private String password;
    private String name;

    @Transient
    private String token;
}
