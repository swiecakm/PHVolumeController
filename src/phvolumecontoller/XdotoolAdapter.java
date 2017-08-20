package phvolumecontoller;

import java.io.IOException;

public class XdotoolAdapter {
    public static void sendKey(String keyCode) {
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
