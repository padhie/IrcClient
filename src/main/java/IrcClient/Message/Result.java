package IrcClient.Message;

import IrcClient.Struct.Channel;
import IrcClient.Struct.Message;
import IrcClient.Struct.User;
import IrcClient.Message.Event.Events;
import org.jetbrains.annotations.Nullable;

public class Result {
    public final String server;
    public final Type type;
    public final User user;
    public final Channel channel;
    public final Message message;
    @Nullable public final Events events;

    public Result (
        String server,
        Type type,
        User user,
        Channel channel,
        Message message,
        @Nullable Events events
    ) {
        this.server = server;
        this.type = type;
        this.user = user;
        this.channel = channel;
        this.message = message;
        this.events = events;
    }
}
