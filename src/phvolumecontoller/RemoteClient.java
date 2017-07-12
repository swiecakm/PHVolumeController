package phvolumecontoller;

import java.net.InetAddress;

/**
 * Created by root on 12.07.17.
 */
public class RemoteClient {
    private InetAddress _address;

    public RemoteClient(InetAddress address){
        _address = address;
    }

    public InetAddress getAddress() {
        return _address;
    }
}
