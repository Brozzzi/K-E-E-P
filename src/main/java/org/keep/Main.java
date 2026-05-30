package org.keep;

import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.util.*;
import java.util.concurrent.TimeUnit;

import Gamepad.Gamepad;
import de.gurkenlabs.input4j.InputComponent;
import de.gurkenlabs.input4j.InputDevice;
import de.gurkenlabs.input4j.InputDevices;
import de.gurkenlabs.input4j.components.XInput;


public class Main {
    Map<InputComponent.ID, Boolean> buttons = new HashMap<>();

    void main() throws InterruptedException, IOException {

        // Setup
        InputDevice selectedDevice = null;
        Gamepad selectedGamepad = null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        List<InputDevice> existingDevices = new ArrayList<>();
        var inputDevicesPlugin = InputDevices.init();

        Farben farbe = new Farben();

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
                            System.out.println("Gerät auswählen");
                            var controllerEingabe = scanner.next();
                            scanner.nextLine();
                            var eingabeInt = Integer.parseInt(controllerEingabe);
                            selectedDevice = existingDevices.get(eingabeInt - 1);


                            var liste = selectedDevice.getComponents();
                            for (var component : liste) {
                                this.buttons.put(component.getId(), false);
                            }

                            for (var component : liste) {
                                selectedDevice.onButtonPressed(component.getId(), buttonPressed(component));
                                selectedDevice.onButtonReleased(component.getId(), buttonReleased(component));
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
                        System.out.println("Drücke einen Knopf um ihn anzuzeigen");

//                        selectedDevice.onButtonPressed(component.getId(), buttonPressed(component));
//                        selectedDevice.onButtonReleased(component.getId(), buttonReleased(component));
                        var knöppe = selectedDevice.getComponents();

                        var läuft = true;
                        while (läuft) {
                            selectedDevice.poll();
                            Thread.sleep(16);  // ~60 FPS polling rate
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

    public Runnable buttonPressed(InputComponent button) {
        return () -> System.out.println("✓ Knopf gedrückt: " + button.getId().name);
    }

    public Runnable buttonReleased(InputComponent button) {
        return () -> System.out.println("✗ Knopf losgelassen: " + button.getId().name);
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

