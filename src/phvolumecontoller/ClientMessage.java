package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientMessage {
    private DatagramSocket _socket;
    private DatagramPacket _packet;
    private String _textRecieved;
    private InetAddress _clientAddress;

    private ClientMessage(DatagramSocket socket, DatagramPacket packet) {
        _socket = socket;
        _packet = packet;
        _textRecieved = new String(packet.getData()).trim();
        _clientAddress = packet.getAddress();
    }

    public static ClientMessage recieve(DatagramSocket socket) throws IOException {
        byte[] buff = new byte[100];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        System.out.println("Recieving packet!!!");
        socket.receive(packet);
        return new ClientMessage(socket, packet);
    }

    public void sendResponse(String message) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, _clientAddress, _socket.getLocalPort());
        _socket.send(responsePacket);
    }

    public String toString() {
        return _textRecieved;
    }

    public InetAddress getSenderAddress() {
        return _clientAddress;
    }

}
