package de.padhie.ircclient.Message;

import de.padhie.ircclient.Struct.Channel;
import de.padhie.ircclient.Struct.Message;
import de.padhie.ircclient.Struct.Param;
import de.padhie.ircclient.Struct.User;
import de.padhie.ircclient.Message.Event.Events;
import org.jetbrains.annotations.Nullable;

public class MessageBuilder {
    public final String raw;

    public String username = "";
    public Type type = Type.UNKNOWN;
    public String channel = "";
    public boolean action = false;
    public String text = "";
    public String server = "";
    @Nullable public Events events = null;
    public MetaItem[] metaItems = new MetaItem[0];

    public MessageBuilder (String raw) {
        this.raw = raw;
    }

    public Result build () {
        Param[] params = new Param[this.metaItems.length];
        for (int i = 0; i < this.metaItems.length; i++) {
            MetaItem item = this.metaItems[i];
            params[i] = new Param(item.key, item.value);
        }

        String clearChannel = this.channel.isEmpty()
            ? this.channel
            : this.channel.substring(1);

        Message message = new Message(this.text, this.raw, params);
        if (this.action) {
            message.markAsAction();
        }

        return new Result(
            this.server,
            this.type,
            new User(this.username, params),
            new Channel(this.channel, clearChannel),
            message,
            this.events
        );
    }
}
