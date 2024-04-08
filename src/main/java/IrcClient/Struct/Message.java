package IrcClient.Struct;

import org.jetbrains.annotations.Nullable;

public class Message {
    private final Param[] params;
    private final String _raw;

    private String _message;
    private boolean action = false;

    public Message (String message) {
        this._message = message;
        this._raw = "";
        this.params = new Param[0];
    }

    public Message (String message, String raw) {
        this._message = message;
        this._raw = raw;
        this.params = new Param[0];
    }

    public Message (String message, String raw, Param[] params) {
        this._message = message;
        this._raw = raw;
        this.params = params;
    }

    public Message setMessage(String message) {
        this._message = message;
        return this;
    }

    public String getMessage() {
        return this._message;
    }

    public String[] getMessageParts() {
        return this.getMessage().split(" ");
    }

    /**
     * Start with 0 for the first word if the message
     */
    public String getMessagePart(Integer part) {
        String[] parts = this.getMessageParts();
        if (parts.length >= part) {
            if (parts[part] != null) {
                return parts[part];
            }
        }
        return null;
    }

    public String getRaw () {
        return _raw;
    }

    @Nullable
    public Param getParam(String key) {
        for (Param param : this.params) {
            if (param.key.equals(key)) {
                return param;
            }
        }

        return null;
    }

    public Param getParamWithDefault(String key) {
        Param param = this.getParam(key);

        return param != null
            ? param
            : new Param(key, "");
    }

    public boolean isWhisper () {
        Param param = this.getParam(Param.KEY_WHISPER);

        return param != null && param.value.equalsIgnoreCase("1");
    }

    public void markAsAction () {
        this.action = true;
    }

    public boolean isAction () {
        return this.action;
    }

    public boolean equals (Message message) {
        return this._message.equalsIgnoreCase(message._message)
            && this._raw.equalsIgnoreCase(message._raw);
    }
}
