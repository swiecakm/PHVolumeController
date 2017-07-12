package phvolumecontoller;

/**
 * Created by root on 13.07.17.
 */
public class MediaCommandsInvoker implements RemoteMessageReceivedListener {
    @Override
    public void onMessageReceived(String receivedMessage) {
        Commands matchedCommand = Commands.get(receivedMessage);
        if (matchedCommand != null) {
            matchedCommand.execute();
        }
    }
}
