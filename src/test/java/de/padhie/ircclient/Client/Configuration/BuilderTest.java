package de.padhie.ircclient.Client.Configuration;

import de.padhie.ircclient.Communication.Reader.NoopReader;
import de.padhie.ircclient.Communication.Reader.ReaderInterface;
import de.padhie.ircclient.Communication.Writter.NoopWriter;
import de.padhie.ircclient.Communication.Writter.WriterInterface;
import de.padhie.ircclient.Configuration.Builder;
import de.padhie.ircclient.Configuration.Configuration;
import de.padhie.ircclient.EventListener.EventListenerInterface;
import de.padhie.ircclient.EventListener.NoopEventListener;
import de.padhie.ircclient.Logger.LoggerInterface;
import de.padhie.ircclient.Logger.NoopLogger;
import de.padhie.ircclient.Message.MessageParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BuilderTest {
    @Test
    public void testClone () {
        Integer port = 9999;
        EventListenerInterface listener = new NoopEventListener();
        MessageParser messageParser = new MessageParser();
        WriterInterface writer = new NoopWriter();
        ReaderInterface reader = new NoopReader();
        LoggerInterface logger = new NoopLogger();
        String[] preLine = new String[] {"pre"};
        String[] postLine = new String[] {"post"};

        Configuration config = (new Builder())
            .withVerbose()
            .withPort(port)
            .withCharset("A")
            .withUsername("B")
            .withPassword("C")
            .withChannel("D")
            .withServer("E")
            .withPreLine(preLine)
            .withPostLine(postLine)
            .withEventListener(listener)
            .withMessageParser(messageParser)
            .withReader(reader)
            .withWriter(writer)
            .withLogger(logger)
            .build();

        assertTrue(config.verbose);
        assertSame(port, config.port);
        assertSame("A", config.charset);
        assertSame("B", config.username);
        assertSame("C", config.password);
        assertSame("D", config.channel);
        assertSame("E", config.server);
        assertSame(preLine, config.preLines);
        assertSame(postLine, config.postLines);
        assertSame(listener, config.listener);
        assertSame(messageParser, config.messageParser);
        assertSame(reader, config.reader);
        assertSame(writer, config.writer);
        assertSame(logger, config.logger);
    }
}
