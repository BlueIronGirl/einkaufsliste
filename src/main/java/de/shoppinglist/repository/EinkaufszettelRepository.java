package de.shoppinglist.repository;

import de.shoppinglist.entity.Einkaufszettel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for the User-Entity
 */
public interface EinkaufszettelRepository extends JpaRepository<Einkaufszettel, Long> {
    List<Einkaufszettel> findByUsersIdAndGeloeschtFalse(Long userId);
}
