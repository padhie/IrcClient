package de.padhie.ircclient.Client.Message.Visitor;

import Test.Helper;
import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.Visitor.UserVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserVisitorTest {
    @Test
    public void testVisitWithInvalidLine () {
        String[] lines = new String[] {
            "",
            "1 1",
            "1 1 1",
            "1 1 !a@b.c",
            "1 1 1 !a@b.c",
        };

        for (String line: lines) {
            MessageBuilder messageBuilder = new MessageBuilder(line);
            (new UserVisitor()).visit(messageBuilder);

            assertEquals("", messageBuilder.username);
        }
    }

    @Test
    public void testVisitWithValidLine () {
        String line = Helper.loadFixture("Model/line_normal-user.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new UserVisitor()).visit(messageBuilder);

        assertEquals("padhiebot", messageBuilder.username);
    }
}
