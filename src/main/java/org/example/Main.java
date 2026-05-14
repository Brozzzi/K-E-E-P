package org.example;

import java.io.IOException;
import java.util.Scanner;

import de.gurkenlabs.input4j.InputDevice;
import de.gurkenlabs.input4j.InputDevicePlugin;
import de.gurkenlabs.input4j.InputDevices;

public class Main {
    static void main() {
        // Setup
        InputDevice selectedDevice;
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

                    try (var inputDevices = InputDevices.init()) {
                        while (!inputDevices.getAll().isEmpty()){
                            var number = 1;
                            for (var inputDevice : inputDevices.getAll()){
                                inputDevice.poll();
                                System.out.println(number + ". " + inputDevice.getProductName() + ":" + inputDevice.getComponents());
                                number++;
                            }
                        }

//                        if (inputDevice == null) {
//                            System.out.println("Kein Gerät gefunden");
//                            return;
//                        var inputDevice = inputDevices.getAll().stream().findFirst().orElse(null);
//
//                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);

                    }

                    System.out.println("Funktion noch in Arbeit. Drücke Enter um fortzufahren...");
                    scanner.nextLine();
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