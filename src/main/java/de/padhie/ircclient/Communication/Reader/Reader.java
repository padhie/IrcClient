package de.padhie.ircclient.Communication.Reader;

import de.padhie.ircclient.Configuration.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Reader implements ReaderInterface {
    @Nullable private Configuration config = null;
    @Nullable private BufferedReader bufferedReader = null;

    @Override
    public void setConfig (@NotNull Configuration config) {
        this.config = config;
    }

    @Override
    public void init (Socket socket) {
        if (this.config == null) {
            return;
        }

        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), this.config.charset));
        } catch (IOException exception) {
            this.config.listener.onError("reader.init", exception);
        }
    }

    @Override
    public @Nullable String readLine () {
        if (this.bufferedReader == null) {
            return null;
        }

        try {
            return this.bufferedReader.readLine();
        } catch (IOException exception) {
            if (this.config != null) {
                this.config.listener.onError("readLine", exception);
            }
        }

        return null;
    }
}
