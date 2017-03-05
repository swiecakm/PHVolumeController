package phvolumecontoller;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Commands
{
	VOL_UP("UP"),
	VOL_DOWN("DOWN"),
	WELCOME_MESSAGE("TEST_WELCOME"),
	RESPONSE_MESSAGE("TEST_RESPONSE");
	
	private String _command;
	private static final Map<String,Commands> ENUM_MAP;

	Commands(String command)
	{
        _command = command;
    }
	public String getCommand()
	{
	    return _command;
	}
	public byte[] getBytes()
	{
	    return _command.getBytes();
	}
	
	static
	{
	    Map<String,Commands> map = new ConcurrentHashMap<String,Commands>();
	    for (Commands instance : Commands.values())
	    {
	      map.put(instance.getCommand(),instance);
	    }
	    ENUM_MAP = Collections.unmodifiableMap(map);
	}
	
	public static Commands get(String command)
	{
		return ENUM_MAP.get(command);
	}
}
