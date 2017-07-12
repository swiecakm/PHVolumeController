package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CommandsInterpretingServer implements Runnable {
    private static String _hostName = "0.0.0.0";
    private static int _portNumber = 8888;
    private static InetAddress _acceptedClientAddress = null;

    @Override
    public void run() {
        System.out.println("Starting server!!!");
        try (DatagramSocket socket = new DatagramSocket(_portNumber, InetAddress.getByName(_hostName))) {
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

                if (receivedMessage.toString().equals(Commands.WELCOME_MESSAGE.toString())) {
                    receivedMessage.sendResponse(Commands.RESPONSE_MESSAGE.toString());
                    _acceptedClientAddress = receivedMessage.getSenderAddress();
                    System.out.println("Client accepted");
                } else if (receivedMessage.getSenderAddress().equals(_acceptedClientAddress)) {
                    interpretCommand(receivedMessage.toString());
                }
            } catch (IOException e) {
                System.out.printf("IO error in server-client connection: %s%n", e);
            }
        }
    }

    private void displayMessageInfo(ClientMessage message) {
        System.out.printf("Packet received from client %s%n", message.getSenderAddress());
        System.out.printf("Packet received data: %s%n", message.toString());
    }

    private void interpretCommand(String receivedMessage) {
        Commands matchedCommand = Commands.get(receivedMessage);
        if (matchedCommand != null) {
            matchedCommand.execute();
        }
    }

    public static CommandsInterpretingServer getInstance() {
        return NetServerHolder.INSTANCE;
    }

    private static class NetServerHolder {
        private static CommandsInterpretingServer INSTANCE = new CommandsInterpretingServer();
    }

}
