package IrcClient.Struct;

public class Param
{
    public final static String KEY_BROADCASTER = "broadcaster";
    public final static String KEY_MOD = "mod";
    public final static String KEY_SUB = "subscriber";
    public final static String KEY_VIP = "vip";
    public final static String KEY_BADGED = "badges";
    public final static String KEY_WHISPER = "whisper";

    public final static String BADGED_BROADCASTER = "broadcaster";
    public final static String BADGED_MOD = "badges";
    public final static String BADGED_SUB = "subscriber";
    public final static String BADGED_VIP = "vip";

    public final String key;
    public final String value;

    public Param (String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public boolean isValue(String value)
    {
        return this.value.equals(value);
    }
}
