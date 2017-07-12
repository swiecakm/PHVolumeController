package phvolumecontoller;

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

    public static ConnectionSettings getInstance(){
        return new ConnectionSettings();
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

}
