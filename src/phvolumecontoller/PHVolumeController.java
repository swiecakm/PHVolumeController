package phvolumecontoller;

import java.io.*;
import java.net.*;

public class PHVolumeController
{
	private static float _volValue = (float)0.6;
	
	public static void main(String[] args)
	{
		Audio.setMasterOutputVolume(_volValue);
		NetServer server = NetServer.getInstance();
		server.run();		
	}
}
