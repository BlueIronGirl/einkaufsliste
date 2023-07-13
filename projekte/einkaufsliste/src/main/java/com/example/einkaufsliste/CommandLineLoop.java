package com.example.einkaufsliste;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CommandLineLoop {

    private final ArtikelRepository artikelRepository;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CommandLineLoop(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
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

            String line = br.readLine().trim();

            switch (line) {
                case "a" -> System.out.println(artikelRepository.findAll());
                case "c" -> {
                    Artikel artikel = new Artikel();
                    leseArtikelDatenEin(artikel);
                }
                case "e" -> {
                    Artikel artikel = new Artikel();
                    System.out.println("Id: ");
                    long id = Long.parseLong(br.readLine().trim());
                    artikel.setId(id);
                    leseArtikelDatenEin(artikel);
                }
                case "d" -> {
                    System.out.println("Id: ");
                    long id = Long.parseLong(br.readLine().trim());
                    artikelRepository.deleteById(id);
                }
                default -> {
                    return;
                }

            }
        }
    }

    private void leseArtikelDatenEin(Artikel artikel) {
        artikel.setName(readString("Name: "));
        artikel.setAnzahl(readInteger("Anzahl: "));
        artikel.setGekauft(readBoolean("Gekauft: "));
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
