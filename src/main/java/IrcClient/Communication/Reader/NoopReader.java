package IrcClient.Communication.Reader;

import IrcClient.Configuration.Configuration;

import java.net.Socket;

public class NoopReader implements ReaderInterface {
    @Override
    public void setConfig (Configuration config) {
    }

    @Override
    public void init (Socket socket) {
    }

    @Override
    public String readLine () {
        return null;
    }
}
