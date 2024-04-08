package IrcClient.Message.Visitor;

import IrcClient.Message.MessageBuilder;
import IrcClient.Message.MetaItem;

import java.util.StringJoiner;

public class MetaVisitor implements VisitorInterface {
    @Override
    public void visit (MessageBuilder messageBuilder) {
        String[] rawParts = messageBuilder.raw.split(" ");
        if (rawParts.length == 0 || rawParts[0].isEmpty()) {
            return;
        }

        String meta = rawParts[0].substring(1);
        String[] segments = meta.split(";");

        for (String segment : segments) {
            String[] itemSegment = segment.split("=");
            if (itemSegment.length < 1) {
                continue;
            }

            messageBuilder.metaCollection.add(new MetaItem(
                itemSegment[0],
                itemSegment.length == 2
                    ? itemSegment[1]
                    : this.combineMultipleValueSegments(itemSegment)
            ));
        }
    }

    private String combineMultipleValueSegments(String[] segments) {
        boolean first = true;
        StringJoiner joiner = new StringJoiner("");

        for (String segment : segments) {
            if (first) {
                first = false;
                continue;
            }

            joiner.add(segment);
        }

        return joiner.toString();
    }
}
