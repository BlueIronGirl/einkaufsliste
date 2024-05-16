package de.shoppinglist.repository;

import de.shoppinglist.entity.Einkaufszettel;
import de.shoppinglist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the User-Entity
 */
public interface EinkaufszettelRepository extends JpaRepository<Einkaufszettel, Long> {
    List<Einkaufszettel> findByUsersId(Long userId);
}
