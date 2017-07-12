package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RemoteMessage {
    private DatagramSocket _socket;
    private String _textReceived;
    private InetAddress _clientAddress;

    private RemoteMessage(DatagramSocket socket, DatagramPacket packet) {
        _socket = socket;
        _textReceived = new String(packet.getData()).trim();
        _clientAddress = packet.getAddress();
    }

    public static RemoteMessage receive(DatagramSocket socket) throws IOException {
        byte[] buff = new byte[100];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        System.out.println("Waiting for packet.");
        socket.receive(packet);
        return new RemoteMessage(socket, packet);
    }

    public void sendResponse(String message) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, _clientAddress, _socket.getLocalPort());
        _socket.send(responsePacket);
    }

    public String toString() {
        return _textReceived;
    }

    public InetAddress getSenderAddress() {
        return _clientAddress;
    }

    public boolean isSentBy(RemoteClient client){
        return client.getAddress().equals(_clientAddress);
    }

    public boolean equals(String text){
        return text.equals(_textReceived);
    }

    public void displayInfo() {
        System.out.printf("Packet received from client %s%n", getSenderAddress());
        System.out.printf("Packet received data: %s%n",  toString());
    }

}
