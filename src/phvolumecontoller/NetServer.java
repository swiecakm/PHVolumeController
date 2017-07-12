package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetServer implements Runnable {
    private static String _hostName = "0.0.0.0";
    private static int _portNumber = 8888;
    private static InetAddress _acceptedClientAddress = null;

    @Override
    public void run() {
        System.out.println("Starting server!!!");
        try (DatagramSocket socket = new DatagramSocket(_portNumber, InetAddress.getByName(_hostName));) {
            socket.setBroadcast(true);
            startServer(socket);
        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }

    private void startServer(DatagramSocket socket) {
        while (true) {
            try {
                ClientMessage recievedMessage = ClientMessage.recieve(socket);
                displayMessageInfo(recievedMessage);

                if (recievedMessage.toString().equals(Commands.WELCOME_MESSAGE.toString())) {
                    recievedMessage.sendResponse(Commands.RESPONSE_MESSAGE.toString());
                    _acceptedClientAddress = recievedMessage.getSenderAddress();
                    System.out.println("Client accepted");
                } else if (recievedMessage.getSenderAddress().equals(_acceptedClientAddress)) {
                    interpretCommand(recievedMessage.toString());
                }
            } catch (IOException e) {
                System.out.println("IO error in server-client connection: " + e);
            }
        }
    }

    private void displayMessageInfo(ClientMessage message) {
        System.out.println("Packet recieved from client " + message.getSenderAddress());
        System.out.println("Packet recieved data: " + message.toString());
    }

    private void interpretCommand(String recievedMessage) {
        Commands matchedCommand = Commands.get(recievedMessage);
        if (matchedCommand != null) {
            matchedCommand.execute();
        }
    }

    public static NetServer getInstance() {
        return NetServerHolder.INSTANCE;
    }

    private static class NetServerHolder {
        private static NetServer INSTANCE = new NetServer();
    }

}
