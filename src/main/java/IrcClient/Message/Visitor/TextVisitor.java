package IrcClient.Message.Visitor;

import IrcClient.Message.MessageBuilder;

public class TextVisitor implements VisitorInterface {
    @Override
    public void visit (MessageBuilder messageBuilder) {
        String raw = messageBuilder.raw;

        boolean isAction = raw.contains(" :ACTION ");
        if (isAction) {
            raw = raw.replace(" :ACTION ", " :");
        }

        String[] rawParts = raw.split(" ");
        if (rawParts.length < 5) {
            return;
        }

        StringBuilder text = new StringBuilder();
        int skipParts = 4;
        int index = 0;

        for (int i=4; i<rawParts.length; i++) {
            text.append(rawParts[i]).append(" ");
        }

        messageBuilder.action = isAction;
        messageBuilder.text = text.toString().trim().substring(1);
    }
}
