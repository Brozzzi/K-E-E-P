package org.example;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import de.gurkenlabs.input4j.InputComponent;
import de.gurkenlabs.input4j.InputDevice;
import de.gurkenlabs.input4j.InputDevices;

public class Main {
    Map<InputComponent.ID, Boolean> buttons = new HashMap<>();


    void main() {

        // Setup
        InputDevice selectedDevice = null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        List<InputDevice> existingDevices = new ArrayList<>();

        while (running) {
            // Terminal bereinigen
//            System.out.print("\033[H\033[2J");
//            clearScreen();


            System.out.print("\033\143");



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


                            var liste = selectedDevice.getComponents();
                            for (var component : liste) {
                                this.buttons.put(component.getId(), false);
                            }

                            for (var component : liste) {
                                selectedDevice.onButtonPressed(component.getId(), buttonPressed(component));
                                selectedDevice.onButtonReleased(component.getId(), buttonReleased(component));
                            }
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
                        System.out.println("Drücke einen Knopf um ihn anzuzeigen");
                        var ausführung = true;
                        while (ausführung) {
                            try {
//                                selectedDevice.poll();

                                List<String> controllerButtons = new ArrayList<>();
                                for (var button : this.buttons.entrySet()) {
                                    if (button.getValue() == true) {
                                        controllerButtons.add(button.getKey().toString());
                                    }
                                }
                                if (!controllerButtons.isEmpty()) {
                                    System.out.print("\r");
                                    System.out.print(controllerButtons);
                                }

                                TimeUnit.MILLISECONDS.sleep(300);
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

    public Runnable buttonPressed(InputComponent button){
        this.buttons.put(button.getId(), true);
        return null;
    }

    public Runnable buttonReleased(InputComponent button){
        this.buttons.put(button.getId(), false);
        return null;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}