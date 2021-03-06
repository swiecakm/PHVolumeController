package phvolumecontoller;

import org.jetbrains.annotations.Contract;

/**
 * Created by root on 12.07.17.
 */
public class ConnectionSettings {
    private final String _hostName = "0.0.0.0";
    private final int _portNumber = 8888;
    private final String _welcomeMessage = "TEST_WELCOME";
    private final String _welcomeResponse = "TEST_RESPONSE";


    private ConnectionSettings(){
    }

    @Contract(pure = true)
    public static ConnectionSettings getInstance(){
        return ConnectionSettingsHolder.INSTANCE;
    }

    public String getHostName() {
        return _hostName;
    }

    public int getPortNumber() {
        return _portNumber;
    }

    public String getExpectedWelcomeMessage() {
        return _welcomeMessage;
    }

    public String getWelcomeResponse() {
        return _welcomeResponse;
    }


    private static class ConnectionSettingsHolder {
        private static ConnectionSettings INSTANCE = new ConnectionSettings();
    }

}
