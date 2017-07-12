package phvolumecontoller;

public class PHVolumeController{
    public static void main(String[] args) {
        CommandsReceivingServer server = CommandsReceivingServer.getInstance();
        server.setListener(new MediaCommandsInvoker());
        server.run();
    }
}
