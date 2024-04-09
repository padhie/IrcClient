package de.padhie.ircclient.Client.Message.Visitor;

import Test.Helper;
import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.Type;
import de.padhie.ircclient.Message.Visitor.TypeVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeVisitorTest {
    @Test
    public void testVisitWithInvalidLine () {
        String[] lines = new String[] {
            "",
            "1",
            "1 1",
        };

        for (String line: lines) {
            MessageBuilder messageBuilder = new MessageBuilder(line);
            (new TypeVisitor()).visit(messageBuilder);

            assertEquals(Type.UNKNOWN, messageBuilder.type);
        }
    }

    @Test
    public void testVisitWithValidLine () {
        String line = Helper.loadFixture("Model/line_normal-user.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new TypeVisitor()).visit(messageBuilder);

        assertEquals(Type.PRIVMSG, messageBuilder.type);
    }

    @Test
    public void testVisitWithPing () {
        String line = "PING :anyChannel";

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new TypeVisitor()).visit(messageBuilder);

        assertEquals(Type.PING, messageBuilder.type);
    }
}
