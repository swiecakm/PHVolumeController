package phvolumecontoller;

import java.awt.AWTException;
import java.awt.Robot;

public class KeySender
{
	private Robot InitializeKeyRobot()
	{
		Robot robot = null;
		try 
        {
			robot = new Robot();
		}
        catch (AWTException e)
        {
			System.out.println("Cannot initialize key robot: " + e);
		}
		return robot;
	}
	
	public void sendKey()
	{
		Robot robot = InitializeKeyRobot();
		robot.keyPress(100);
		robot.keyRelease(100);
	}
}
