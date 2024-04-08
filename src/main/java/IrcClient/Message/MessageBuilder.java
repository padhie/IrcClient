package IrcClient.Message;

import IrcClient.Struct.Channel;
import IrcClient.Struct.Message;
import IrcClient.Struct.Param;
import IrcClient.Struct.User;
import IrcClient.Message.Event.Events;
import org.jetbrains.annotations.Nullable;

public class MessageBuilder {
    public final String raw;
    public final MetaCollection metaCollection = new MetaCollection();

    public String username = "";
    public Type type = Type.UNKNOWN;
    public String channel = "";
    public boolean action = false;
    public String text = "";
    public String server = "";
    @Nullable public Events events = null;

    public MessageBuilder (String raw) {
        this.raw = raw;
    }

    public Result build () {
        Param[] params = new Param[this.metaCollection.size()];
        for (int i = 0; i < this.metaCollection.size(); i++) {
            MetaItem item = this.metaCollection.get(i);
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
