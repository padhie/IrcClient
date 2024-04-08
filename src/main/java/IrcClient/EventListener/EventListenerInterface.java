package IrcClient.EventListener;

import IrcClient.Configuration.Configuration;
import IrcClient.Client.Client;
import IrcClient.Struct.Message;
import IrcClient.Struct.User;
import IrcClient.Message.Event.Events;

public interface EventListenerInterface {
    void setHandler (Client handler);

    /**
     * Function to set the Configuration
     */
    void setConfig (Configuration configuration);

    /**
     * Function call if server send a PING-Request
     * PONG will be answered automatically
     */
    void onPing ();

    /**
     * Function call if the library will connect to server
     */
    void onConnect ();

    /**
     * Function call if the library will log in into channel
     *
     * @param user Userobject with all detected userstats
     */
    void onLogin (User user);

    /**
     * Function call if the library leave the channel
     *
     * @param channel Name of the channel with #
     */
    void onLeave (String channel);

    /**
     * Function call if the library will disconnect from server
     *
     * @param channel Name of the channel with #
     */
    void onDisconnect (String channel);

    /**
     * Function call if a user join the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     */
    void onJoin (String channel, User user);

    /**
     * Function call if a user leave the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     */
    void onPart (String channel, User user);

    /**
     * Function call if a user send normal message is writing into the channel (not from the library)
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     * @param message Messageobject will all detected messagestats
     */
    void onIncomingMessage (String channel, User user, Message message);

    /**
     * Function call if the library get a whisper
     *
     * @param user    Userobject with all detected userstats
     * @param message Messageobject will all detected messagestats
     */
    void onWhisper (User user, Message message);

    /**
     * Function call if the library send a normal message into the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     * @param message Messageobject will all detected messagestats
     */
    void onOutgoingMessage (String channel, User user, Message message);

    /**
     * Function call if a user will be OP for the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     */
    void onOp (String channel, User user);

    /**
     * Function call if a user will be DE-OP for the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     */
    void onDeop (String channel, User user);

    /**
     * Function call if a MODE-Request send to the channel
     * OP and DE-OP is included
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     */
    void onMode (String channel, User user);

    /**
     * Function call if a NOTICE-Request send to the channel
     *
     * @param channel Name of the channel with #
     * @param message Messageobject will all detected messagestats
     */
    void onNotice (String channel, Message message);

    /**
     * Function call if a USERSTATE-Request send to the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     */
    void onUserstate (String channel, User user);

    /**
     * Function call if a CLEARCHAT-Request send to the channel
     *
     * @param channel Name of the channel with #
     */
    void onClearchat (String channel);

    /**
     * Function call if a USERNOTICE-Request send to the channel
     *
     * @param channel Name of the channel with #
     * @param message Messageobject will all detected messagestats
     */
    void onUsernotice (String channel, Message message);

    /**
     * Function call if a HOSTTARGET-Request send to the channel
     *
     * @param channel Name of the channel with #
     * @param target  Name of the channel witch is now hosting
     * @param amount  Amount of user, witch are sending to the hosting channel
     */
    void onHostTarget (String channel, String target, int amount);

    /**
     * Function call if a USERNONCE-Request send to the channel
     *
     * @param channel Name of the channel with #
     * @param events Detected Events of the message
     */
    void onEvents(String channel, Events events);

    /**
     * Function call if a ROOMSTATE-Request send to the channel
     *
     * @param channel Name of the channel with #
     */
    void onRoomstate (String channel);

    /**
     * Function call for all unknown requests in the channel
     *
     * @param channel Name of the channel with #
     * @param user    Userobject with all detected userstats
     * @param message Messageobject will all detected messagestats
     */
    void onUnknown (String channel, User user, Message message);

    /**
     * Function call for every error curing listen
     *
     * @param method    Method of the error
     * @param exception Error
     */
    void onError(String method, Throwable exception);
}