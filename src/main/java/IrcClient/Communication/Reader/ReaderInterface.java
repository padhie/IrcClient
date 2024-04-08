package IrcClient.Communication.Reader;

import IrcClient.Configuration.Configuration;
import org.jetbrains.annotations.Nullable;

import java.net.Socket;

public interface ReaderInterface {
    void setConfig (Configuration config);
    void init (Socket socket);
    @Nullable String readLine ();
}
