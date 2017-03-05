package phvolumecontoller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetServer implements Runnable
{
	private static String _hostName = "0.0.0.0";
	private static int _portNumber = 8888;
	private static InetAddress _acceptedClientAddress = null;

	@Override
	public void run()
	{	
		System.out.println("Starting server!!!");
		try(DatagramSocket socket = new DatagramSocket(_portNumber,InetAddress.getByName(_hostName));)
		{
			startServer(socket);
		}
		catch (Exception e)
		{
			System.out.println("Server error: " + e);
		}
	}

	private void startServer(DatagramSocket socket)
	{
		while(true)
		{
			try
			{
				ClientMessage message = ClientMessage.recieve(socket);
						
				System.out.println("Packet recieved from client " + message.getSenderAddress());
				System.out.println("Packet recieved data: " + message.toString());
				
				if(message.toString().equals(Commands.WELCOME_MESSAGE.getCommand()))
				{
					message.sendWelcomeResponse();
					_acceptedClientAddress = message.getSenderAddress();
					System.out.println("Client accepted");
				}
				else if(message.getSenderAddress().equals(_acceptedClientAddress))
				{
					interpretCommand(message.toString());
				}
			}
			catch(IOException e)
			{
				System.out.println("IO error in server+client connection: " + e);
			}
		}
	}

	private void interpretCommand(String recievedMessage)
	{
		Commands matchedCommand = Commands.get(recievedMessage);
		if(matchedCommand == null){return;}
		
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
	
	public static NetServer getInstance()
	{
		return NetServerHolder.INSTANCE;
	}
	
	private static class NetServerHolder
	{
		private static NetServer INSTANCE = new NetServer();
	}
	
}
