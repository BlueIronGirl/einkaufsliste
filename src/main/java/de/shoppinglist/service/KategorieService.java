package de.shoppinglist.service;

import de.shoppinglist.entity.Kategorie;
import de.shoppinglist.repository.KategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KategorieService {
    private final KategorieRepository kategorieRepository;

    public List<Kategorie> selectAllKategorien() {
        return kategorieRepository.findAll();
    }
}
