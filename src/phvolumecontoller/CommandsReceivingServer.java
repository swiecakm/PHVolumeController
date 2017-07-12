package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CommandsReceivingServer implements Runnable {
    private RemoteClient _acceptedClient = null;
    private ConnectionSettings _settings = ConnectionSettings.getInstance();
    private CommandReceivedListener _listener = null;

    public void setListener(CommandReceivedListener listener) {
        _listener = listener;
    }

    @Override
    public void run() {
        System.out.println("Starting server!!!");
        try (DatagramSocket socket = new DatagramSocket(_settings.getPortNumber(), InetAddress.getByName(_settings.getHostName()))) {
            socket.setBroadcast(true);
            startServer(socket);
        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }

    private void startServer(DatagramSocket socket) {
        while (true) {
            try {
                ClientMessage receivedMessage = ClientMessage.receive(socket);
                displayMessageInfo(receivedMessage);
                handleReceivedMessage(receivedMessage);
            } catch (IOException e) {
                System.out.printf("IO error in server-client connection: %s%n", e);
            }
        }
    }

    private void displayMessageInfo(ClientMessage message) {
        System.out.printf("Packet received from client %s%n", message.getSenderAddress());
        System.out.printf("Packet received data: %s%n", message.toString());
    }

    private void handleReceivedMessage(ClientMessage receivedMessage) throws IOException {
        if (receivedMessage.toString().equals(_settings.getExpectedWelcomeMessage())) {
            receivedMessage.sendResponse(_settings.getWelcomeResponse());
            acceptMessageSender(receivedMessage);
        } else if (_acceptedClient != null && receivedMessage.getSenderAddress().equals(_acceptedClient.getAddress())) {
            _listener.onCommandReceived(receivedMessage.toString());
        }
    }

    private void acceptMessageSender(ClientMessage receivedMessage) {
        _acceptedClient = new RemoteClient(receivedMessage.getSenderAddress());
        System.out.println("Client accepted");
    }

    public static CommandsReceivingServer getInstance() {
        return NetServerHolder.INSTANCE;
    }

    private static class NetServerHolder {
        private static CommandsReceivingServer INSTANCE = new CommandsReceivingServer();
    }

}
