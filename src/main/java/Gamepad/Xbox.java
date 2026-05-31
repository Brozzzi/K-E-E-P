package Gamepad;

import de.gurkenlabs.input4j.InputComponent;
import de.gurkenlabs.input4j.components.Button;

import java.util.HashMap;
import java.util.Map;

public class Xbox extends Gamepad {
// TODO: Richtig machen
    Map<String, String> GamepadButtons = new HashMap<>(
            Button.BUTTON_0 = InputComponent.ID.get("A")
    );
}
