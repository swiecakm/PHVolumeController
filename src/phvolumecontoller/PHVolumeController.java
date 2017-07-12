package phvolumecontoller;

public class PHVolumeController {
    public static void main(String[] args) {
        CommandsInterpretingServer server = CommandsInterpretingServer.getInstance();
        server.run();
    }
}
