package de.padhie.ircclient.Message.Visitor;

import de.padhie.ircclient.Message.MessageBuilder;

public class ServerVisitor implements VisitorInterface {
    @Override
    public void visit (MessageBuilder messageBuilder) {
        String[] rawParts = messageBuilder.raw.split(" ");
        if (rawParts.length < 2) {
            return;
        }

        String segment = rawParts[1];
        segment = segment.substring(segment.indexOf("!") + 1);
        segment = segment.substring(segment.indexOf("@") + 1);
        segment = segment.substring(segment.indexOf(".") + 1);

        messageBuilder.server = segment;
    }
}
