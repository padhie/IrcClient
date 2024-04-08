package IrcClient.EventListener;

import IrcClient.Configuration.Configuration;
import IrcClient.Client.Client;
import IrcClient.Struct.Message;
import IrcClient.Struct.User;
import IrcClient.Message.Event.Events;

public class NoopEventListener implements EventListenerInterface {
    @Override
    public void setHandler (Client handler) {
    }

    @Override
    public void setConfig (Configuration configuration) {
    }

    @Override
    public void onPing () {
    }

    @Override
    public void onConnect () {
    }

    @Override
    public void onLogin (User user) {
    }

    @Override
    public void onLeave (String channel) {
    }

    @Override
    public void onDisconnect (String channel) {
    }

    @Override
    public void onJoin (String channel, User user) {
    }

    @Override
    public void onPart (String channel, User user) {
    }

    @Override
    public void onIncomingMessage (String channel, User user, Message message) {
    }

    @Override
    public void onWhisper (User user, Message message) {
    }

    @Override
    public void onOutgoingMessage (String channel, User user, Message message) {
    }

    @Override
    public void onOp (String channel, User user) {
    }

    @Override
    public void onDeop (String channel, User user) {
    }

    @Override
    public void onMode (String channel, User user) {
    }

    @Override
    public void onNotice (String channel, Message message) {
    }

    @Override
    public void onUserstate (String channel, User user) {
    }

    @Override
    public void onClearchat (String channel) {
    }

    @Override
    public void onHostTarget (String channel, String target, int amount) {
    }

    @Override
    public void onEvents(String channel, Events events) {
    }

    @Override
    public void onUsernotice (String channel, Message message) {
    }

    @Override
    public void onRoomstate (String channel) {
    }

    @Override
    public void onUnknown (String channel, User user, Message message) {
    }

    @Override
    public void onError (String method, Throwable exception) {
    }
}
