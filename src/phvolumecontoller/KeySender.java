package phvolumecontoller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Event;

public class KeySender {
    public static void sendKey(int keyCode) {
        String command = "xdotool key " + keyCode;
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
        } catch (IOException e) {
            System.out.print(e);
        } catch (InterruptedException e) {
            System.out.print(e);
        }
    }
}
