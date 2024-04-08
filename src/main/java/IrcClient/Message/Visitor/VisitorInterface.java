package IrcClient.Message.Visitor;

import IrcClient.Message.MessageBuilder;

public interface VisitorInterface {
    void visit(MessageBuilder messageBuilder);
}
