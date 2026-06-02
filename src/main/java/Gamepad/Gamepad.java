package Gamepad;

import de.gurkenlabs.input4j.InputComponent;

import java.util.HashMap;
import java.util.Map;

public class Gamepad {
    protected String gamepadName;
    protected final Map<InputComponent.ID, String> buttonNames = new HashMap<>();

    public Gamepad(String name) {
        this.gamepadName = name;
    }

    public String getGamepadName() {
        return gamepadName;
    }

    public void setGamepadName(String name) {
        gamepadName = name;
    }

    public String getButtonName(InputComponent.ID id) {
        if (id == null) {
            return null;
        }
        return buttonNames.getOrDefault(id, id.name);
    }

}
