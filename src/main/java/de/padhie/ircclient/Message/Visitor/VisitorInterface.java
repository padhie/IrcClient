package de.padhie.ircclient.Message.Visitor;

import de.padhie.ircclient.Message.MessageBuilder;

public interface VisitorInterface {
    void visit(MessageBuilder messageBuilder);
}
