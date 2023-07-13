package com.example.einkaufsliste;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EinkaufslisteApplication implements CommandLineRunner {

	private final ArtikelRepository artikelRepository;

	public EinkaufslisteApplication(ArtikelRepository artikelRepository) {
		this.artikelRepository = artikelRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(EinkaufslisteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new CommandLineLoop(artikelRepository).run();
	}
}
