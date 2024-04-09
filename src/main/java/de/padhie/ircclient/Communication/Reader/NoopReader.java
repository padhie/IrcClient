package de.padhie.ircclient.Communication.Reader;

import de.padhie.ircclient.Configuration.Configuration;

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
