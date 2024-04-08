package IrcClient.Message.Visitor;

import IrcClient.Message.Event.*;
import IrcClient.Message.MessageBuilder;
import org.jetbrains.annotations.Nullable;

public class EventVisitor implements VisitorInterface {
    @Override
    public void visit (MessageBuilder messageBuilder) {
        String raw = messageBuilder.raw;
        Events events = new Events();

        this.checkMe(raw, events);
        this.checkRaid(raw, events);
        this.checkSub(raw, events);
        this.checkGift(raw, events);

        messageBuilder.events = events;
    }

    private void checkMe (String raw, Events events) {
        int start = raw.indexOf(" :ACTION ");

        if (start < 0) {
            return;
        }

        String message = raw.substring(start + 9);
        events.me = new Me(message);
    }

    private void checkRaid (String raw, Events events) {
        if (!raw.contains(";msg-id=raid;")) {
            return;
        }

        String raider = this.extractValueOfRawString(raw, "msg-param-displayName");
        String viewerCount = this.extractValueOfRawString(raw, "msg-param-viewerCount");
        String message = this.extractValueOfRawString(raw, "system-msg");
        if (raider == null || viewerCount == null || message == null) {
            return;
        }

        events.raid = new Raid(raider, Integer.parseInt(viewerCount), message);
    }

    private void checkSub (String raw, Events events) {
        // only sub or gift is possible
        if (events.gift != null) {
            return;
        }

        if (!raw.contains(";msg-id=resub;")) {
            return;
        }

        String user = this.extractValueOfRawString(raw, "display-name");
        String plan = this.extractValueOfRawString(raw, "msg-param-sub-plan");
        String month = this.extractValueOfRawString(raw, "msg-param-months");
        String cumulativeMonth = this.extractValueOfRawString(raw, "msg-param-cumulative-months");
        if (user == null || plan == null || month == null || cumulativeMonth == null) {
            return;
        }

        String gifted = this.extractValueOfRawString(raw, "msg-param-was-gifted");
        String multimonthDuration = this.extractValueOfRawString(raw, "msg-param-multimonth-duration");
        String multimonthTenure = this.extractValueOfRawString(raw, "msg-param-multimonth-tenure");
        String shouldShareStreak = this.extractValueOfRawString(raw, "msg-param-should-share-streak");
        String message = this.extractValueOfRawString(raw, "system-msg");

        events.sub = new Sub(
            user,
            SubPlan.fromIrcString(plan),
            Integer.parseInt(month),
            Integer.parseInt(cumulativeMonth),
            gifted != null && gifted.equalsIgnoreCase("true"),
            Integer.parseInt(multimonthDuration != null ? multimonthDuration : "0"),
            Integer.parseInt(multimonthTenure != null ? multimonthTenure : "0"),
            Integer.parseInt(shouldShareStreak != null ? shouldShareStreak : "0"),
            message != null ? message : ""
        );
    }

    private void checkGift (String raw, Events events) {
        if (!raw.contains(";msg-id=subgift;")) {
            return;
        }

        String recipientUser = this.extractValueOfRawString(raw, "msg-param-recipient-display-name");
        String login = this.extractValueOfRawString(raw, "login");
        String plan = this.extractValueOfRawString(raw, "msg-param-sub-plan");
        String month = this.extractValueOfRawString(raw, "msg-param-months");
        String giftMonth = this.extractValueOfRawString(raw, "msg-param-gift-months");
        if (recipientUser == null || plan == null || month == null || giftMonth == null) {
            return;
        }

        String message = this.extractValueOfRawString(raw, "system-msg");

        // only sub or gift is possible
        events.sub = null;
        events.gift = new Gift(
            login,
            recipientUser,
            SubPlan.fromIrcString(plan),
            Integer.parseInt(month),
            Integer.parseInt(giftMonth),
            message != null ? message : ""
        );
    }

    @Nullable
    private String extractValueOfRawString (String raw, String key) {
        String parameterString = raw.substring(1, raw.indexOf(" "));
        parameterString = ";" + parameterString + ";";

        String keyString = ";" + key + "=";

        int startOfValue = parameterString.indexOf(keyString);
        if (startOfValue < 0) {
            return null;
        }

        parameterString = parameterString.substring(startOfValue + keyString.length());

        int endOfValue = parameterString.indexOf(";");
        if (endOfValue == -1) {
            endOfValue = parameterString.length();
        }

        return parameterString.substring(0, endOfValue);
    }
}
