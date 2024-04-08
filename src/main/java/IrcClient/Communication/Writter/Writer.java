package IrcClient.Communication.Writter;

import IrcClient.Configuration.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Writer implements WriterInterface {
    @Nullable private Configuration config = null;
    @Nullable private BufferedWriter bufferedWriter = null;

    public void setConfig (@NotNull Configuration config) {
        this.config = config;
    }

    public void init (Socket socket) {
        if (this.config == null) {
            return;
        }

        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), this.config.charset));
        } catch (IOException exception) {
            this.config.listener.onError("writer.init", exception);
        }
    }

    public void writeLine (@NotNull String line) {
        if (this.bufferedWriter == null) {
            return;
        }

        line = line.trim();

        try {
            this.bufferedWriter.write(line + "\r\n");
            this.bufferedWriter.flush();
        } catch (IOException exception) {
            if (this.config != null) {
                this.config.listener.onError("writeLine", exception);
            }
        }
    }
}
