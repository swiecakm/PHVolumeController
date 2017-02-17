package phvolumecontoller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DiscoveryThread implements Runnable {

	private DatagramSocket _socket;
	private static String _hostName = "0.0.0.0";
	private static int _portNumber = 8888;
	
	@Override
	public void run()
	{	
		try
		{
			System.out.println("Starting server!!!");
			_socket = new DatagramSocket(_portNumber,InetAddress.getByName(_hostName));
			_socket.setBroadcast(true);
			
			while(true)
			{
				System.out.println("Recieving packet!!!");
				
				byte[] buff = new byte[100];
				DatagramPacket packet = new DatagramPacket(buff, buff.length);
				_socket.receive(packet);
				System.out.println("Packet recieved from client " + packet.getAddress().getHostAddress());
				System.out.println("Packet recieved data: " + new String(packet.getData()));
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public static DiscoveryThread getInstance()
	{
		return DiscoveryThreadHolder.INSTANCE;
	}
	
	private static class DiscoveryThreadHolder
	{
		private static DiscoveryThread INSTANCE = new DiscoveryThread();
	}
	
}
