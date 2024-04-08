package IrcClient.Message.Visitor;

import IrcClient.Message.MessageBuilder;
import IrcClient.Message.Type;

public class TypeVisitor implements VisitorInterface {
    public void visit(MessageBuilder messageBuilder) {
        String[] rawParts = messageBuilder.raw.split(" ");
        messageBuilder.type = Type.UNKNOWN;

        if (rawParts.length == 0) {
            return;
        }

        if (rawParts.length >= 3) {
            messageBuilder.type = Type.fromString(rawParts[2]);
        }

        if (
            messageBuilder.raw.contains(" PING ")
            || messageBuilder.raw.startsWith("PING ")
            ||messageBuilder.raw.endsWith(" PING")
        ) {
            messageBuilder.type = Type.PING;
        }
    }
}
