package phvolumecontoller;

import java.io.*;
import java.net.*;

public class PHVolumeController
{	
	public static void main(String[] args)
	{
		NetServer server = NetServer.getInstance();
		server.run();
	}
}
