package IrcClient.Message.Visitor;

import IrcClient.Message.MessageBuilder;

public class ChannelVisitor implements VisitorInterface {
    @Override
    public void visit (MessageBuilder messageBuilder) {
        String[] rawParts = messageBuilder.raw.split(" ");
        if (rawParts.length >= 4) {
            messageBuilder.channel = rawParts[3];
        }
    }
}
