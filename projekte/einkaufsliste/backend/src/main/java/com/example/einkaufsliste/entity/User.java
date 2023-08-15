package com.example.einkaufsliste.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
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
