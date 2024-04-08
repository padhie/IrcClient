package IrcClient.Configuration;

import IrcClient.Communication.Reader.ReaderInterface;
import IrcClient.Communication.Writter.WriterInterface;
import IrcClient.EventListener.EventListenerInterface;
import IrcClient.Logger.LoggerInterface;
import IrcClient.Message.MessageParser;

public class Configuration {
    public final boolean verbose;
    public final String charset;
    public final String username;
    public final String password;
    public final String channel;
    public final String server;
    public final Integer port;
    public final EventListenerInterface listener;
    public final ReaderInterface reader;
    public final WriterInterface writer;
    public final MessageParser messageParser;
    public final LoggerInterface logger;
    public final String[] preLines;
    public final String[] postLines;

    public Configuration (
        boolean verbose,
        String charset,
        String username,
        String password,
        String channel,
        String server,
        Integer port,
        EventListenerInterface listeners,
        ReaderInterface reader,
        WriterInterface writer,
        MessageParser messageParser,
        LoggerInterface logger,
        String[] preLines,
        String[] postLines
    ) {
        this.verbose = verbose;
        this.charset = charset;
        this.username = username;
        this.password = password;
        this.channel = channel;
        this.server = server;
        this.port = port;
        this.listener = listeners;
        this.reader = reader;
        this.writer = writer;
        this.messageParser = messageParser;
        this.logger = logger;
        this.preLines = preLines;
        this.postLines = postLines;
    }
}
