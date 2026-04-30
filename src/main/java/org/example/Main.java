package org.example;

import java.util.Scanner;

public class Main {
    static void main() {
        // Setup
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Terminal bereinigen
            System.out.print("\033[H\033[2J");

            System.out.println("Willkommen beim Kontroller-Eingabge-Erkennungs-Programm");
            //TODO: Gucken ob ein Controller ausgewählt wurde, Status im Print anzeigen
            System.out.println("Status: ");
            System.out.println();
            System.out.println(" 1. Kontroller Auswählen");
            System.out.println(" 2. Kontroller testen");
            System.out.println(" 3. Programm schließen");

            System.out.println("Wählen sie ein Menüpunkt aus (1-3): ");
            String eingabe = scanner.next();
            scanner.nextLine();

            switch (eingabe) {
                case "1":
                    System.out.println("Funktion noch in Arbeit. Drücke Enter um fortzufahren...");
//                    scanner.nextLine();



                    break;
                case "2":
                    System.out.println("Funktion noch in Arbeit. Drücke Enter um fortzufahren...");
                    scanner.nextLine();

                    break;
                case "3":
                    System.out.println("Programm wird beendet. Drücke Enter um fortzufahren...");
                    scanner.nextLine();
                    running = false;

                    break;
            }
        }
    }
}