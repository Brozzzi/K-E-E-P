package org.keep;

import java.io.IOException;
import java.security.Key;
import java.security.KeyException;
import java.util.*;

import de.gurkenlabs.input4j.InputComponent;
import de.gurkenlabs.input4j.InputDevice;
import de.gurkenlabs.input4j.InputDevices;

public class Main {

    Map<InputComponent.ID, Boolean> buttons = new HashMap<>();

    void main() throws InterruptedException, IOException, KeyException {
        // Setup
        InputDevice selectedDevice = null;
        Scanner scanner = new Scanner(System.in);
        Farben farbe = new Farben();
        List<InputDevice> existingDevices = new ArrayList<>();
        var inputDevicesPlugin = InputDevices.init();
        boolean running = true;

        while (running) {

            clearScreen();

            System.out.println("Willkommen beim " + farbe.fett + "Kontroller-Eingabge-Erkennungs-Programm" + farbe.reset);
            System.out.println();
            if (null == selectedDevice) {
                System.out.println("Status:" + farbe.rot + " ---Kein Kontroller ausgewählt---" + farbe.reset);
            } else {
                System.out.println("Status: " + farbe.grün + selectedDevice.getProductName() + farbe.reset);
            }
            System.out.println();
            System.out.println(" 1. Kontroller Auswählen");
            System.out.println(" 2. Kontroller testen");
            System.out.println(" 3. Programm schließen");
            System.out.println();
            System.out.println("Wählen sie ein Menüpunkt aus (1-3): ");

            String eingabe = scanner.next();
            scanner.nextLine();

            switch (eingabe) {
                case "1":
                    if (inputDevicesPlugin != null) {
                        if (!inputDevicesPlugin.getAll().isEmpty()) {
                            clearScreen();

                            var number = 1;
                            existingDevices.clear();
                            for (var inputDevice : inputDevicesPlugin.getAll()) {
                                System.out.println(number + ". " + inputDevice.getProductName());
                                existingDevices.add(inputDevice);
                                number++;
                            }
                            System.out.println("Wähle ein Gerät aus indem du die Nummer eingibst:");
                            try {
                                var controllerEingabe = scanner.next();
                                scanner.nextLine();
                                var eingabeInt = Integer.parseInt(controllerEingabe);

                                if (eingabeInt < 1 || eingabeInt > existingDevices.size()) {
                                    clearScreen();
                                    System.out.println(farbe.rot + "❌ Ungültige Auswahl! Bitte wähle eine Nummer zwischen 1 und " + existingDevices.size() + farbe.reset);
                                    System.out.println("Drücke eine Taste um fortzufahren...");
                                    scanner.nextLine();
                                    continue;
                                }

                                selectedDevice = existingDevices.get(eingabeInt - 1);

                                var liste = selectedDevice.getComponents();
                                for (var component : liste) {
                                    this.buttons.put(component.getId(), false);
                                }

                                for (var component : liste) {
                                    selectedDevice.onButtonPressed(component.getId(), buttonPressed(component));
                                    selectedDevice.onButtonReleased(component.getId(), buttonReleased(component));
                                }
                            } catch (NumberFormatException e) {
                                clearScreen();
                                System.out.println(farbe.rot + "❌ Fehler: Bitte gib eine Zahl ein!" + farbe.reset);
                                System.out.println("Drücke eine Taste um fortzufahren...");
                                scanner.nextLine();
                                continue;
                            }
                        } else {
                            clearScreen();
                            System.out.println(farbe.gelb + "Keine Controller gefunden" + farbe.reset);
                            System.out.println("Drücke belibige Taste um fortzufahren");

                            scanner.nextLine();
                            continue;
                        }
                    } else {
                        clearScreen();
                        System.out.println(farbe.rot + "Fehler: InputDevices konnten nicht initialisiert werden" + farbe.reset);
                        System.out.println("Drücke belibige Taste um fortzufahren");
                        scanner.nextLine();
                    }
                    break;

                case "2":
                    if (selectedDevice == null) {
                        clearScreen();
                        System.out.println(farbe.gelb + "Keine Controller ausgewählt" + farbe.reset);
                        System.out.println("Drücke belibige Taste um fortzufahren");
                        scanner.nextLine();
                    } else {
                        clearScreen();
                        System.out.println("Ausgewählt: " + farbe.grün + selectedDevice.getProductName() + farbe.reset);
                        System.out.println("Drücke einen Knopf um ihn anzuzeigen | Gebe ");

                        var läuft = true;

                        while (läuft) {
                            try {
                                selectedDevice.poll();
                                Thread.sleep(16); // ~60 FPS polling rate
                            } catch (InterruptedException e) {
                                läuft = false;
                            }
                        }
                    }
                    break;


                case "3":
                    clearScreen();
                    System.out.println("Programm wird beendet. Drücke Enter um fortzufahren...");
                    scanner.nextLine();

                    if (inputDevicesPlugin != null) {
                        inputDevicesPlugin.close();
                    }
                    running = false;

                    break;
            }
        }
    }
    Farben farbe = new Farben();
    public Runnable buttonPressed(InputComponent button) {
        return () -> buttonPressedOutput(button);
    }

    public Runnable buttonReleased(InputComponent button) {
        return () -> buttonReleasedOutput(button);
    }

    public void buttonPressedOutput(InputComponent button) {
        System.out.print("\r");
        System.out.print(farbe.grün + "✓"+ farbe.reset + " Knopf: " + button.getId().name);
    }

    public void buttonReleasedOutput(InputComponent button) {
        System.out.print("\r");
        System.out.print(farbe.rot + "✗" + farbe.reset + " Knopf: " + button.getId().name);
    }

    public static void clearScreen() {
        int zeilen = 50;
        for (int zeile = 0; zeile <= zeilen; zeile++) {
            System.out.println();
        }
    }

    public static void clearScreenSpace(int zeilen) {
        for (int zeile = 0; zeile <= zeilen; zeile++) {
            System.out.println();
        }
    }

}

