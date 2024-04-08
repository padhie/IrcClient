package IrcClient.Communication.Writter;

import IrcClient.Configuration.Configuration;

import java.net.Socket;

public class NoopWriter implements WriterInterface {
    @Override
    public void setConfig (Configuration config) {
    }

    @Override
    public void init (Socket socket) {
    }

    @Override
    public void writeLine (String line) {
    }
}
