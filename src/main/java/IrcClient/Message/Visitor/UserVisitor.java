package IrcClient.Message.Visitor;

import IrcClient.Message.MessageBuilder;

public class UserVisitor implements VisitorInterface {
    @Override
    public void visit (MessageBuilder messageBuilder) {
        String[] rawParts = messageBuilder.raw.split(" ");
        if (rawParts.length < 2) {
            return;
        }

        String segment = rawParts[1];
        int posFirstDelimiter = segment.indexOf("!");
        int posSecondDelimiter = segment.indexOf("@");
        int posThirdDelimiter = segment.indexOf(".");

        if (posFirstDelimiter <= 1 || posSecondDelimiter == -1 || posThirdDelimiter == -1) {
            return;
        }

        String first = segment.substring(1, posFirstDelimiter);
        String second = segment.substring(posFirstDelimiter + 1, posSecondDelimiter);
        String third = segment.substring(posSecondDelimiter + 1, posThirdDelimiter);

        if (first.equalsIgnoreCase(second) && second.equalsIgnoreCase(third)) {
            messageBuilder.username = first;
        }
    }
}
