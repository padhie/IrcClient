package de.padhie.ircclient.Client.Message.Visitor;

import de.padhie.ircclient.Message.Visitor.ChannelVisitor;
import Test.Helper;
import de.padhie.ircclient.Message.MessageBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChannelVisitorTest {
    @Test
    public void testVisitWithInvalidLine () {
        String[] lines = new String[] {
            "",
            "1",
            "1 1",
            "1 1 1",
            "1 1 1",
        };

        for (String line: lines) {
            MessageBuilder messageBuilder = new MessageBuilder(line);
            (new ChannelVisitor()).visit(messageBuilder);

            assertEquals("", messageBuilder.channel);
        }
    }

    @Test
    public void testVisitWithValidLine () {
        String line = Helper.loadFixture("Model/line_normal-user.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new ChannelVisitor()).visit(messageBuilder);

        assertEquals("#padhiebot", messageBuilder.channel);
    }
}
