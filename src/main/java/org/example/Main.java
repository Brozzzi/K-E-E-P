package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import de.gurkenlabs.input4j.InputDevice;
import de.gurkenlabs.input4j.InputDevicePlugin;
import de.gurkenlabs.input4j.InputDevices;

public class Main {
    static void main() {
        // Setup
        InputDevice selectedDevice = null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        List<InputDevice> existingDevices = new ArrayList<>();

        while (running) {
            // Terminal bereinigen
            System.out.print("\033[H\033[2J");

            System.out.println("Willkommen beim Kontroller-Eingabge-Erkennungs-Programm");
            if (null == selectedDevice){
                System.out.println("Ausgewählt: ");
            }
            else {
                System.out.println("Ausgewählt: " +  selectedDevice.getProductName());
            }
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
                        if (!inputDevices.getAll().isEmpty()) {

                            var number = 1;
                            for (var inputDevice : inputDevices.getAll()){
                                System.out.println(number + ". " + inputDevice.getProductName());
                                existingDevices.add(inputDevice);
                                number++;
                            }
                            System.out.println("Gerät auswählen");
                            var controllerEingabe = scanner.next();
                            scanner.nextLine();
                            var eingabeInt = Integer.parseInt(controllerEingabe);
                            selectedDevice = existingDevices.get(eingabeInt - 1);
                        }
                        else {
                            System.out.println("Keine Controller gefunden");
                            System.out.println("Drücke belibige Taste um fortzufahren");

                            scanner.nextLine();
                            continue;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);

                    }
                    break;


                case "2":
                    if (selectedDevice == null) {
                        System.out.println("Keine Controller ausgewählt");
                        System.out.println("Drücke belibige Taste um fortzufahren");
                        scanner.nextLine();
                    }else {
                        System.out.println("Ausgewählt: " + selectedDevice.getProductName());
                        var ausführung = true;
                        while (ausführung) {
                            try {
                                selectedDevice.poll();
                                var liste = selectedDevice.getComponents();
                                for (var component : liste) {
                                    System.out.print(component);
                                }
                                TimeUnit.MILLISECONDS.sleep(500);
                                System.out.print("\r");
                            } catch (Exception e) {
                                ausführung = false;
                            }
                        }
                    }


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