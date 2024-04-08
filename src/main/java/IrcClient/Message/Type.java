package IrcClient.Message;

import org.jetbrains.annotations.NotNull;

public enum Type {
    PING("PING"),
    MODE("MODE"),
    JOIN("JOIN"),
    PART("PART"),
    NOTICE("NOTICE"),
    PRIVMSG("PRIVMSG"),
    WHISPER("WHISPER"),
    USERSTATE("USERSTATE"),
    CLEARCHAT("CLEARCHAT"),
    HOSTTARGET("HOSTTARGET"),
    USERNOTICE("USERNOTICE"),
    ROOMSTATE("ROOMSTATE"),
    UNKNOWN("UNKNOWN");

    public final String type;

    Type (String type) {
        this.type = type;
    }

    @NotNull
    public static Type fromString (String type) {
        try {
            return Type.valueOf(type);
        } catch (Throwable throwable) {
            return Type.UNKNOWN;
        }
    }

    public boolean equal (Type type) {
        return this.type.equalsIgnoreCase(type.toString());
    }

    public String toString () {
        return this.type;
    }
}
