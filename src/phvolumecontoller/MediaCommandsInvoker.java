package phvolumecontoller;

/**
 * Created by root on 13.07.17.
 */
public class MediaCommandsInvoker implements CommandReceivedListener{
    @Override
    public void onCommandReceived(String textCommand) {
        Commands matchedCommand = Commands.get(textCommand);
        if (matchedCommand != null) {
            matchedCommand.execute();
        }
    }
}
