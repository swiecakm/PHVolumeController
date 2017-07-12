package phvolumecontoller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.javafx.robot.FXRobot;

public enum Commands {
    VOL_UP("UP", () -> KeySender.sendKey(123)), //Audio.increaseMasterOutputVolume()),
    VOL_DOWN("DOWN", () -> KeySender.sendKey(122)),
    WELCOME_MESSAGE("TEST_WELCOME", () -> {
    }),
    RESPONSE_MESSAGE("TEST_RESPONSE", () -> {
    });

    private String _text;
    private Command _command;
    private static final Map<String, Commands> ENUM_MAP;

    Commands(String text, Command command) {
        _text = text;
        _command = command;
    }

    public void execute() {
        _command.execute();
    }

    public String toString() {
        return _text;
    }

    public byte[] getBytes() {
        return _text.getBytes();
    }

    static {
        Map<String, Commands> map = new ConcurrentHashMap<String, Commands>();
        for (Commands instance : Commands.values()) {
            map.put(instance.toString(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Commands get(String command) {
        return ENUM_MAP.get(command);
    }
}
