package phvolumecontoller;

public class PHVolumeController{
    public static void main(String[] args) {
        ReceivingMessagesServer server = ReceivingMessagesServer.getInstance();
        server.setListener(new MediaCommandsInvoker());
        server.run();
    }
}
