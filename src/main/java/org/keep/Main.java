package org.keep;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import de.gurkenlabs.input4j.InputComponent;
import de.gurkenlabs.input4j.InputDevice;
import de.gurkenlabs.input4j.InputDevices;
import de.gurkenlabs.input4j.components.XInput;


public class Main {
    Map<InputComponent.ID, Boolean> buttons = new HashMap<>();

    void main() {

        // Setup
        InputDevice selectedDevice = null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        List<InputDevice> existingDevices = new ArrayList<>();

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

                    try (var inputDevices = InputDevices.init()) {
                        if (!inputDevices.getAll().isEmpty()) {
                            clearScreen();

                            var number = 1;
                            for (var inputDevice : inputDevices.getAll()) {
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
                    } catch (IOException e) {
                        throw new RuntimeException(e);

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
                        var läuft = true;
                        while (läuft) {

                            var knöppe = selectedDevice.getComponents();

                            for (var component : knöppe) {

//                                var A = XInput.A.id;
//                                var id = component.getId();

//                                if (XInput.A.id == component.getId()) {
                                    selectedDevice.onButtonPressed(component.getId());
                                        System.out.println(" A ");

//                                }




//                                selectedDevice.onButtonReleased(component.getId(), buttonReleased(component));
                            }

//                            for (var button : this.buttons.entrySet()) {
//                                selectedDevice.onButtonPressed(button.getKey(), () -> {
//                                    System.out.println("Button is jedrückt: " + button.getKey());
//                                });
////                                    System.out.println("Button: " + button.getKey());
//
//                            }
                        }
                    }


                    break;


                case "3":
                    clearScreen();
                    System.out.println("Programm wird beendet. Drücke Enter um fortzufahren...");
                    scanner.nextLine();
                    running = false;

                    break;
            }
        }
    }

    public Runnable buttonPressed(InputComponent button) {
        this.buttons.put(button.getId(), true);
        return null;
    }

    public Runnable buttonReleased(InputComponent button) {
        this.buttons.put(button.getId(), false);
        return null;
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

