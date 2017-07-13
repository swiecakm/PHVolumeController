package phvolumecontoller;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceivingMessagesServer implements Runnable {
    private RemoteClient _pairedRemoteClient = null;
    private ConnectionSettings _settings = ConnectionSettings.getInstance();
    private RemoteMessageReceivedListener _listener = null;

    public void setListener(RemoteMessageReceivedListener listener) {
        _listener = listener;
    }

    @Override
    public void run() {
        System.out.println("Starting server.");
        try (DatagramSocket socket = new DatagramSocket(_settings.getPortNumber(), InetAddress.getByName(_settings.getHostName()))) {
            socket.setBroadcast(true);
            startServer(socket);
        } catch (Exception e) {
            System.out.printf("Server error: %s%n", e);
        }
    }

    private void startServer(DatagramSocket socket) {
        while (true) try {
            RemoteMessage receivedMessage = RemoteMessage.receive(socket);
            receivedMessage.displayInfo();
            handleReceivedMessage(receivedMessage);
        } catch (IOException e) {
            System.out.printf("IO error in server-client connection: %s%n", e);
        }
    }

    private void handleReceivedMessage(RemoteMessage receivedMessage) throws IOException {
        if (receivedMessage.equals(_settings.getExpectedWelcomeMessage())) {
            receivedMessage.sendResponse(_settings.getWelcomeResponse());
            pairWithMessageSender(receivedMessage);
        } else if (_pairedRemoteClient != null && receivedMessage.isSentBy(_pairedRemoteClient)) {
            _listener.onMessageReceived(receivedMessage.toString());
        }
    }

    private void pairWithMessageSender(RemoteMessage receivedMessage) {
        _pairedRemoteClient = new RemoteClient(receivedMessage.getSenderAddress());
        System.out.printf("Paired with remote client: %s%n", _pairedRemoteClient.getAddress());
    }

    @Contract(pure = true)
    public static ReceivingMessagesServer getInstance() {
        return ReceivingMessagesServerHolder.INSTANCE;
    }

    private static class ReceivingMessagesServerHolder {
        private static ReceivingMessagesServer INSTANCE = new ReceivingMessagesServer();
    }

}
