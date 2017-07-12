package phvolumecontoller;

public class PHVolumeController {
    public static void main(String[] args) {
        NetServer server = NetServer.getInstance();
        server.run();
    }
}
