package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetServer implements Runnable
{
	private static String _hostName = "0.0.0.0";
	private static String _clientServerName = null;
	private static int _portNumber = 8888;
	
	@Override
	public void run()
	{	
		try(DatagramSocket socket = new DatagramSocket(_portNumber,InetAddress.getByName(_hostName));)
		{
			System.out.println("Starting server!!!");
			socket.setBroadcast(true);
			
			while(true)
			{
				System.out.println("Recieving packet!!!");
				
				byte[] buff = new byte[100];
				DatagramPacket packet = new DatagramPacket(buff, buff.length);
				socket.receive(packet);
				String recievedMessage = new String(packet.getData()).trim();
				String senderAddress = packet.getAddress().getHostAddress();
						
				System.out.println("Packet recieved from client " + senderAddress);
				System.out.println("Packet recieved data: " + recievedMessage);
				
				if(recievedMessage.equals(Commands.WELCOME_MESSAGE.getCommand()))
				{
					acceptConnection(socket, packet);
				}
				else if(senderAddress.equals(_clientServerName))
				{
					interpretCommand(recievedMessage);
				}
			}	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	private void interpretCommand(String recievedMessage)
	{
		Commands matchedCommand = Commands.get(recievedMessage);
		if(matchedCommand== null){return;}
		
		switch (matchedCommand)
		{
			case VOL_UP:
				System.out.println("Zglosnij");
				break;
			case VOL_DOWN:
				System.out.println("Przycisz");
				break;
			default:
				break;
		}
	}

	private void acceptConnection(DatagramSocket socket, DatagramPacket packet)
			throws InterruptedException, IOException
	{
		System.out.println("Recieved message accepted, sending response");
		Thread.sleep(1000);
		byte[] sendData = Commands.RESPONSE_MESSAGE.getBytes();
		DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),_portNumber);
		socket.send(responsePacket);
		_clientServerName = packet.getAddress().getHostAddress();
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
