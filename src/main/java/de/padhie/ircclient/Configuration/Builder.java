package de.padhie.ircclient.Configuration;

import de.padhie.ircclient.Communication.Reader.NoopReader;
import de.padhie.ircclient.Communication.Reader.ReaderInterface;
import de.padhie.ircclient.Communication.Writter.NoopWriter;
import de.padhie.ircclient.Communication.Writter.WriterInterface;
import de.padhie.ircclient.EventListener.EventListenerInterface;
import de.padhie.ircclient.EventListener.NoopEventListener;
import de.padhie.ircclient.Logger.LoggerInterface;
import de.padhie.ircclient.Logger.NoopLogger;
import de.padhie.ircclient.Message.MessageParser;

public class Builder implements Cloneable {
    private boolean _verbose = false;
    private String _charset = "UTF-8";
    private String _username = "";
    private String _password = "";
    private String _channel = "";
    private String _server = "";
    private Integer _port = 6667;
    private EventListenerInterface _listener = new NoopEventListener();
    private ReaderInterface _reader = new NoopReader();
    private WriterInterface _writer = new NoopWriter();
    private MessageParser _messageParser = new MessageParser();
    private LoggerInterface _logger = new NoopLogger();
    private String[] _preLines = new String[0];
    private String[] _postLines = new String[0];

    public Configuration build () {
        Configuration config = new Configuration(
            this._verbose,
            this._charset,
            this._username,
            this._password,
            this._channel,
            this._server,
            this._port,
            this._listener,
            this._reader,
            this._writer,
            this._messageParser,
            this._logger,
            this._preLines,
            this._postLines
        );

        this._listener.setConfig(config);
        this._reader.setConfig(config);
        this._writer.setConfig(config);

        return config;
    }

    public Builder withVerbose () {
        Builder clone = this.clone();
        clone._verbose = true;
        return clone;
    }

    public Builder withCharset (String charset) {
        Builder clone = this.clone();
        clone._charset = charset;
        return clone;
    }

    public Builder withUsername (String username) {
        Builder clone = this.clone();
        clone._username = username;
        return clone;
    }

    public Builder withPassword (String password) {
        Builder clone = this.clone();
        clone._password = password;
        return clone;
    }

    public Builder withChannel (String channel) {
        Builder clone = this.clone();
        clone._channel = channel;
        return clone;
    }

    public Builder withServer (String server) {
        Builder clone = this.clone();
        clone._server = server;
        return clone;
    }

    public Builder withPort (Integer port) {
        Builder clone = this.clone();
        clone._port = port;
        return clone;
    }

    public Builder withEventListener (EventListenerInterface listener) {
        Builder clone = this.clone();
        clone._listener = listener;
        return clone;
    }

    public Builder withMessageParser (MessageParser messageParser) {
        Builder clone = this.clone();
        clone._messageParser = messageParser;
        return clone;
    }

    public Builder withPreLine (String[] preLines) {
        Builder clone = this.clone();
        clone._preLines = preLines;
        return clone;
    }

    public Builder withPostLine (String[] postLines) {
        Builder clone = this.clone();
        clone._postLines = postLines;
        return clone;
    }

    public Builder withWriter (WriterInterface writer) {
        Builder clone = this.clone();
        clone._writer = writer;
        return clone;
    }

    public Builder withReader (ReaderInterface reader) {
        Builder clone = this.clone();
        clone._reader = reader;
        return clone;
    }

    public Builder withLogger (LoggerInterface logger) {
        Builder clone = this.clone();
        clone._logger = logger;
        return clone;
    }

    protected Builder clone () {
        Builder clone = new Builder();

        clone._verbose = this._verbose;
        clone._charset = this._charset;
        clone._username = this._username;
        clone._password = this._password;
        clone._channel = this._channel;
        clone._server = this._server;
        clone._port = this._port;
        clone._listener = this._listener;
        clone._reader = this._reader;
        clone._writer = this._writer;
        clone._messageParser = this._messageParser;
        clone._logger = this._logger;
        clone._preLines = this._preLines;
        clone._postLines = this._postLines;

        return clone;
    }
}
