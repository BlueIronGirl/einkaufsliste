package com.example.einkaufsliste;

import com.example.einkaufsliste.repository.ArtikelRepository;
import com.example.einkaufsliste.repository.KategorieRepository;
import com.example.einkaufsliste.util.CommandLineLoop;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EinkaufslisteApplication implements CommandLineRunner {

	private final ArtikelRepository artikelRepository;
	private final KategorieRepository kategorieRepository;

	public EinkaufslisteApplication(ArtikelRepository artikelRepository, KategorieRepository kategorieRepository) {
		this.artikelRepository = artikelRepository;
		this.kategorieRepository = kategorieRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(EinkaufslisteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new CommandLineLoop(artikelRepository, kategorieRepository).run();
	}
}
