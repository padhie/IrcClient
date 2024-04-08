package IrcClient.Struct;

import org.jetbrains.annotations.Nullable;

public class User {
    public final String name;
    public final Param[] paramList;

    public User (String user) {
        this.name = user;
        this.paramList = new Param[0];
    }

    public User (String user, Param[] params) {
        this.name = user;
        this.paramList = params;
    }

    @Nullable
    public Param getParam (String key) {
        for (Param param : paramList) {
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

    public boolean hasChannelRight () {
        return this.isInternalDev()
            || this.isMod()
            || this.isBroadcaster();
    }

    public boolean isInternalDev () {
        return this.name.equalsIgnoreCase("padhie");
    }

    public boolean isBroadcaster () {
        return this.getParam(Param.KEY_BROADCASTER) != null
            || this.getParam(Param.BADGED_BROADCASTER) != null;
    }

    public boolean isMod () {
        return this.getParam(Param.KEY_MOD) != null
            || this.getParam(Param.BADGED_MOD) != null;
    }

    public boolean isSub () {
        return this.getParam(Param.KEY_SUB) != null
            || this.getParam(Param.BADGED_SUB) != null;
    }

    public boolean isVip () {
        return this.getParam(Param.KEY_VIP) != null
            || this.getParam(Param.BADGED_VIP) != null;
    }

    public boolean equals (User user) {
        return this.name.equalsIgnoreCase(user.name);
    }
}
