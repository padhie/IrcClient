package de.padhie.ircclient.Client.Message.Visitor;

import Test.Helper;
import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.Visitor.MetaVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetaVisitorTest {
    @Test
    public void testVisitWithInvalidLine () {
        String[] lines = new String[] {
            "",
        };

        for (String line: lines) {
            MessageBuilder messageBuilder = new MessageBuilder(line);
            (new MetaVisitor()).visit(messageBuilder);

            assertEquals("", messageBuilder.channel);
        }
    }

    @Test
    public void testVisitWithValidLine () {
        String line = Helper.loadFixture("Model/line_normal-user.txt");

        MessageBuilder messageBuilder = new MessageBuilder(line);
        (new MetaVisitor()).visit(messageBuilder);

        assertEquals(17, messageBuilder.metaItems.length);

        assertEquals("badge-info", messageBuilder.metaItems[0].key);
        assertEquals("", messageBuilder.metaItems[0].value);
        assertEquals("badges", messageBuilder.metaItems[1].key);
        assertEquals("", messageBuilder.metaItems[1].value);
        assertEquals("client-nonce", messageBuilder.metaItems[2].key);
        assertEquals("9i8h7g6f5e4d3c2b1a", messageBuilder.metaItems[2].value);
        assertEquals("color", messageBuilder.metaItems[3].key);
        assertEquals("#FF0000", messageBuilder.metaItems[3].value);
        assertEquals("display-name", messageBuilder.metaItems[4].key);
        assertEquals("padhiebot", messageBuilder.metaItems[4].value);
        assertEquals("emotes", messageBuilder.metaItems[5].key);
        assertEquals("555555560:83-84/555555584:114-115/1:31-32", messageBuilder.metaItems[5].value);
        assertEquals("first-msg", messageBuilder.metaItems[6].key);
        assertEquals("0", messageBuilder.metaItems[6].value);
        assertEquals("flags", messageBuilder.metaItems[7].key);
        assertEquals("", messageBuilder.metaItems[7].value);
        assertEquals("id", messageBuilder.metaItems[8].key);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", messageBuilder.metaItems[8].value);
        assertEquals("mod", messageBuilder.metaItems[9].key);
        assertEquals("0", messageBuilder.metaItems[9].value);
        assertEquals("returning-chatter", messageBuilder.metaItems[10].key);
        assertEquals("0", messageBuilder.metaItems[10].value);
        assertEquals("room-id", messageBuilder.metaItems[11].key);
        assertEquals("123456789", messageBuilder.metaItems[11].value);
        assertEquals("subscriber", messageBuilder.metaItems[12].key);
        assertEquals("0", messageBuilder.metaItems[12].value);
        assertEquals("tmi-sent-ts", messageBuilder.metaItems[13].key);
        assertEquals("1658754900000", messageBuilder.metaItems[13].value);
        assertEquals("turbo", messageBuilder.metaItems[14].key);
        assertEquals("0", messageBuilder.metaItems[14].value);
        assertEquals("user-id", messageBuilder.metaItems[15].key);
        assertEquals("98765432", messageBuilder.metaItems[15].value);
        assertEquals("user-type", messageBuilder.metaItems[16].key);
        assertEquals("", messageBuilder.metaItems[16].value);
    }
}
