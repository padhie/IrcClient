package de.padhie.ircclient.Client.Message.Visitor;

import Test.Helper;
import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.Visitor.TextVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextVisitorTest {
    @Test
    public void testVisitWithInvalidLine () {
        String[] lines = new String[] {
            "",
            "1",
            "1 1",
        };

        for (String line: lines) {
            MessageBuilder messageBuilder = new MessageBuilder(line);
            (new TextVisitor()).visit(messageBuilder);

            assertEquals("", messageBuilder.text);
        }
    }

    @Test
    public void testVisitWithValidLine () {
        String line = Helper.loadFixture("Model/line_normal-user.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new TextVisitor()).visit(messageBuilder);

        assertEquals("This is a message from an non-sub user, not posting for the very first time.", messageBuilder.text);
        assertFalse(messageBuilder.action);
    }

    @Test
    public void testVisitWithValidLineWithAction () {
        String line = Helper.loadFixture("Model/line_normal-user-with-action.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new TextVisitor()).visit(messageBuilder);

        assertEquals("I am a user who has emphasized his message.", messageBuilder.text);
        assertTrue(messageBuilder.action);
    }
}
