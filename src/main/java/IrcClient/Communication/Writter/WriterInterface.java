package IrcClient.Communication.Writter;

import IrcClient.Configuration.Configuration;

import java.net.Socket;

public interface WriterInterface {
    void setConfig (Configuration config);
    void init (Socket socket);
    void writeLine (String line);
}
