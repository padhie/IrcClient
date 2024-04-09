package de.padhie.ircclient.Client.Message.Visitor;

import Test.Helper;
import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.Visitor.ServerVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerVisitorTest {
    @Test
    public void testVisitWithInvalidLine () {
        String[] lines = new String[] {
            "",
            "1",
        };

        for (String line: lines) {
            MessageBuilder messageBuilder = new MessageBuilder(line);
            (new ServerVisitor()).visit(messageBuilder);

            assertEquals("", messageBuilder.server);
        }
    }

    @Test
    public void testVisitWithValidLine () {
        String line = Helper.loadFixture("Model/line_normal-user.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new ServerVisitor()).visit(messageBuilder);

        assertEquals("tmi.twitch.tv", messageBuilder.server);
    }
}
