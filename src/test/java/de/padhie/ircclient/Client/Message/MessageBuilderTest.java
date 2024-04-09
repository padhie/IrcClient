package de.padhie.ircclient.Client.Message;

import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.MetaItem;
import de.padhie.ircclient.Message.Result;
import de.padhie.ircclient.Message.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MessageBuilderTest {
    @Test
    public void testBuild () {
        MessageBuilder builder = new MessageBuilder("raw");
        builder.username = "username";
        builder.type = Type.PRIVMSG;
        builder.channel = "channel";
        builder.text = "text";
        builder.server = "server";
        builder.metaItems = new MetaItem[]{
            new MetaItem("foo", "bar")
        };

        Result result = builder.build();

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("channel", result.channel.name);
        assertEquals("server", result.server);

        assertEquals("username", result.user.name);
        assertEquals("bar", result.user.getParamWithDefault("foo").value);
        assertNull(result.user.getParam("bar"));

        assertEquals("raw", result.message.getRaw());
        assertEquals("text", result.message.getMessage());
        assertEquals("bar", result.message.getParamWithDefault("foo").value);
        assertNull(result.message.getParam("bar"));
    }
}
