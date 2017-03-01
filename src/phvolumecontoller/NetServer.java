package phvolumecontoller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetServer implements Runnable {

	private DatagramSocket _socket;
	private static String _hostName = "0.0.0.0";
	private static int _portNumber = 8888;
	private static String _serverWelcomMessage = "TEST_WELCOME";
	private static String _serverResponseMessage = "TEST_RESPONSE";
	
	@Override
	public void run()
	{	
		try(DatagramSocket _socket = new DatagramSocket(_portNumber,InetAddress.getByName(_hostName));)
		{
			System.out.println("Starting server!!!");
			_socket.setBroadcast(true);
			
			while(true)
			{
				System.out.println("Recieving packet!!!");
				
				byte[] buff = new byte[100];
				DatagramPacket packet = new DatagramPacket(buff, buff.length);
				_socket.receive(packet);
				String recievedMessage = new String(packet.getData()).trim();
						
				System.out.println("Packet recieved from client " + packet.getAddress().getHostAddress());
				System.out.println("Packet recieved data: " + recievedMessage);
				
				if(recievedMessage.equals(_serverWelcomMessage))
				{
					System.out.println("Recieved message accepted, sending response");
					Thread.sleep(1000);
					byte[] sendData = _serverResponseMessage.getBytes();
					DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),_portNumber);
					_socket.send(responsePacket);					
				}		
			}	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public static NetServer getInstance()
	{
		return NetServerHolder.INSTANCE;
	}
	
	private static class NetServerHolder
	{
		private static NetServer INSTANCE = new NetServer();
	}
	
}
