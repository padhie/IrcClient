package de.padhie.ircclient.Message;

import de.padhie.ircclient.Message.Visitor.*;

public class MessageParser {
    private final VisitorInterface[] messageVisitors = new VisitorInterface[] {
        new TypeVisitor(),
        new MetaVisitor(),
        new UserVisitor(),
        new ServerVisitor(),
        new ChannelVisitor(),
        new TextVisitor(),
        new EventVisitor(),
    };


    public Result parse (String line) {
        MessageBuilder messageBuilder = new MessageBuilder(line);
        if (line.startsWith(":tmi.")) {
            return messageBuilder.build();
        }

        if (!line.startsWith("@")) {
            line = "@ " + line;
            messageBuilder = new MessageBuilder(line);
        }

        for (VisitorInterface visitor : this.messageVisitors) {
            visitor.visit(messageBuilder);
        }

        return messageBuilder.build();
    }
}
