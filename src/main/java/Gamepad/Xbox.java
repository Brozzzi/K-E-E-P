package Gamepad;

import de.gurkenlabs.input4j.InputComponent;


public class Xbox extends Gamepad {
    public Xbox() {
        super("Xbox Controller");
        buttonNames.put(InputComponent.ID.getButton(0), "A");
        buttonNames.put(InputComponent.ID.getButton(1), "B");
        buttonNames.put(InputComponent.ID.getButton(2), "X");
        buttonNames.put(InputComponent.ID.getButton(3), "Y");
        buttonNames.put(InputComponent.ID.getButton(4), "LB");
        buttonNames.put(InputComponent.ID.getButton(5), "RB");
        buttonNames.put(InputComponent.ID.getButton(6), "Back");
        buttonNames.put(InputComponent.ID.getButton(7), "Start");
        buttonNames.put(InputComponent.ID.getButton(8), "L-Stick");
        buttonNames.put(InputComponent.ID.getButton(9), "R-Stick");
    }
}
