package de.padhie.ircclient.Communication.Reader;

import de.padhie.ircclient.Configuration.Configuration;
import org.jetbrains.annotations.Nullable;

import java.net.Socket;

public interface ReaderInterface {
    void setConfig (Configuration config);
    void init (Socket socket);
    @Nullable String readLine ();
}
