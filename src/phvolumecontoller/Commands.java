package phvolumecontoller;

import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Commands {
    VOL_UP("UP", () -> XdotoolAdapter.sendKey(123)),
    VOL_DOWN("DOWN", () -> XdotoolAdapter.sendKey(122));

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

    @Contract(pure = true)
    public String toString() {
        return _text;
    }

    //Hash map for searching for command by name
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
