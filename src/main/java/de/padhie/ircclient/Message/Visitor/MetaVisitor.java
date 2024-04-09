package de.padhie.ircclient.Message.Visitor;

import de.padhie.ircclient.Message.MessageBuilder;
import de.padhie.ircclient.Message.MetaItem;

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
        segments = this.cleanupInvalidSegments(segments);

        messageBuilder.metaItems = this.generateMetaItems(segments);
    }

    private String[] cleanupInvalidSegments(String[] segments) {
        int counter = 0;
        for (String segment : segments) {
            String[] itemSegment = segment.split("=");
            if (itemSegment.length < 1) {
                continue;
            }

            counter++;
        }

        if (counter == segments.length) {
            return segments;
        }

        String[] cleanSegments = new String[counter];
        int index = 0;

        for (String segment : segments) {
            String[] itemSegment = segment.split("=");
            if (itemSegment.length < 1) {
                continue;
            }

            cleanSegments[index] = segment;
            index++;
        }

        return cleanSegments;
    }

    private MetaItem[] generateMetaItems(String[] segments) {
        MetaItem[] metaItems = new MetaItem[segments.length];
        int index = 0;

        for (String segment : segments) {
            String[] itemSegment = segment.split("=");

            String value = itemSegment.length == 2
                ? itemSegment[1]
                : this.combineMultipleValueSegments(itemSegment);

            metaItems[index] = new MetaItem(itemSegment[0], value);
            index++;
        }

        return metaItems;
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
