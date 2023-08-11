package com.example.einkaufsliste.util;

import com.example.einkaufsliste.entity.Artikel;
import com.example.einkaufsliste.repository.ArtikelRepository;
import com.example.einkaufsliste.entity.Kategorie;
import com.example.einkaufsliste.repository.KategorieRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CommandLineLoop {

    private final ArtikelRepository artikelRepository;
    private final KategorieRepository kategorieRepository;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CommandLineLoop(ArtikelRepository artikelRepository, KategorieRepository kategorieRepository) {
        this.artikelRepository = artikelRepository;
        this.kategorieRepository = kategorieRepository;
    }

    public void run() throws IOException {
        System.out.println("Herzlich Willkommen in der Spring Boot Einkaufsliste.");

        while (true) {
            System.out.println("Menü:");
            System.out.println("a: alle Artikel");
            System.out.println("l: Lade einen Artikel");
            System.out.println("c: Erzeuge einen Artikel");
            System.out.println("e: Ersetze einen Artikel");
            System.out.println("d: Lösche einen Artikel");
            System.out.println("ak: alle Kategorien");
            System.out.println("ck: Erzeuge eine Kategorie");
            System.out.println("ek: Ersetze eine Kategorie");
            System.out.println("dk: Lösche eine Kategorie");

            String line = br.readLine().trim();

            switch (line) {
                // Artikel
                case "a" -> System.out.println(artikelRepository.findAll());
                case "l" -> System.out.println(artikelRepository.findById((long) readInteger("Id: ")));
                case "c" -> {
                    Artikel artikel = new Artikel();
                    artikel.setErstellungsZeitpunkt(LocalDateTime.now());
                    leseArtikelDatenEinUndSpeichere(artikel);
                }
                case "e" -> {
                    System.out.println("Id: ");
                    long id = Long.parseLong(br.readLine().trim());

                    Optional<Artikel> artikelOptional = artikelRepository.findById(id);
                    if (artikelOptional.isPresent()) {
                        Artikel artikel = artikelOptional.get();
                        leseArtikelDatenEinUndSpeichere(artikel);
                    } else {
                        System.err.println("Der Artikel wurde nicht gefunden!");
                    }

                }
                case "d" -> {
                    System.out.println("Id: ");
                    long id = Long.parseLong(br.readLine().trim());
                    artikelRepository.deleteById(id);
                }
                // Kategorie
                case "ak" -> System.out.println(kategorieRepository.findAll());
                case "ck" -> {
                    Kategorie kategorie = new Kategorie();
                    leseKategorieDatenEinUndSpeichere(kategorie);
                }
                case "ek" -> {
                    System.out.println("Id: ");
                    long id = Long.parseLong(br.readLine().trim());

                    Optional<Kategorie> kategorieOptional = kategorieRepository.findById(id);
                    if (kategorieOptional.isPresent()) {
                        Kategorie kategorie = kategorieOptional.get();
                        leseKategorieDatenEinUndSpeichere(kategorie);
                    } else {
                        System.err.println("Der Artikel wurde nicht gefunden!");
                    }
                }
                case "dk" -> {
                    System.out.println("Id: ");
                    long id = Long.parseLong(br.readLine().trim());
                    kategorieRepository.deleteById(id);
                }
                default -> {
                    System.err.println("Ungültige Eingabe!");
                    return;
                }

            }
        }
    }

    private void leseKategorieDatenEinUndSpeichere(Kategorie kategorie) {
        kategorie.setName(readString("Name: "));
        kategorieRepository.save(kategorie);
    }

    private void leseArtikelDatenEinUndSpeichere(Artikel artikel) {
        artikel.setName(readString("Name: "));
        artikel.setAnzahl(readInteger("Anzahl: "));
        artikel.setGekauft(readBoolean("Gekauft: "));
        if (artikel.isGekauft()) {
            artikel.setKaufZeitpunkt(LocalDateTime.now());
        }
        String kategorie = readString("Kategorie: ");
        artikel.setKategorie(kategorieRepository.findByNameIgnoreCase(kategorie));
        artikelRepository.save(artikel);
    }

    private String readString(String message) {
        String result;

        while (true) {
            System.out.println(message);
            try {
                result = br.readLine().trim();
                if (result.length() > 0) {
                    break;
                }
                System.out.println("Ungültige Eingabe!");
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe!");
            }

        }

        return result;
    }

    private int readInteger(String message) {
        int result;

        while (true) {
            System.out.println(message);
            try {
                result = Integer.parseInt(br.readLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe!");
            }
        }

        return result;
    }

    private boolean readBoolean(String message) {
        boolean result;

        while (true) {
            System.out.println(message);
            try {
                result = Boolean.parseBoolean(br.readLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe!");
            }
        }

        return result;
    }
}
