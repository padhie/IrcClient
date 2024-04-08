package IrcClient.Client;

import IrcClient.Configuration.Configuration;
import IrcClient.EventListener.EventListenerInterface;
import IrcClient.Message.Result;
import IrcClient.Message.Type;
import IrcClient.Struct.User;

import java.io.IOException;
import java.net.Socket;

public class Client implements ClientInterface{
    private final Configuration _config;

    public Client (Configuration config) {
        this._config = config;
        this._config.listener.setHandler(this);
    }

    public boolean login () {
        try {
            @SuppressWarnings ("resource") Socket socket = new Socket(this._config.server, this._config.port);
            this._config.writer.init(socket);
            this._config.reader.init(socket);
        } catch (IOException exception) {
            this._config.listener.onError("login-connect", exception);

            return false;
        }

        if (!this._config.password.isEmpty()) {
            this._config.logger.addLog("using pass: ***");
            this._config.writer.writeLine("PASS " + this._config.password);
        }

        User loggedInUser = new User("");
        if (!this._config.username.isEmpty()) {
            loggedInUser = new User(this._config.username);
            
            this._config.logger.addLog("using nick: " + this._config.username);
            this._config.writer.writeLine("NICK " + this._config.username);
            
            this._config.logger.addLog("using login: " + this._config.username);
            this._config.writer.writeLine("USER " + this._config.username + " 8 * : Java IRC IrcPLib Client");
        }

        // Read lines from the server until it tells us we have connected.
        String line;
        while ((line = this._config.reader.readLine()) != null) {
            if (line.contains("004")) {
                // We are now logged in.
                this._config.logger.addLog("Login success.");
                this._config.listener.onLogin(loggedInUser);
                break;
            } else if (line.contains("433")) {
                this._config.logger.addLog("Nickname is already in use.");
                return false;
            }
        }

        return true;
    }

    public void join () {
        String channel = this._config.channel;
        if (channel.isEmpty()) {
            return;
        }

        for (String preLine : this._config.preLines) {
            this._config.writer.writeLine(preLine);
        }

        this._config.writer.writeLine("JOIN " + channel);

        for (String postLine : this._config.postLines) {
            this._config.writer.writeLine(postLine);
        }
    }

    public void start () {
        class ircThread extends Thread {
            @Override
            public void run () {
                _start();
            }
        }
        Thread t = new ircThread();
        t.start();
    }

    public void leave () {
        String channel = this._config.channel;

        this._config.writer.writeLine("PART " + channel);
        this._config.listener.onLeave(channel);
    }

    private void _start () {
        String line;

        // Keep reading lines from the server.
        while ((line = this._config.reader.readLine()) != null) {
            this._config.logger.addLog(line);
            if (!line.startsWith(":tmi.")) {
                Result parserResult = this._config.messageParser.parse(line);
                this.triggerEvent(parserResult);
            }
        }
    }

    private void triggerEvent (Result parserResult) {
        if (parserResult.type.equal(Type.PING)) {
            this._config.writer.writeLine("PONG " + parserResult.message.getRaw().substring(5));
            this._config.listener.onPing();
            return;
        }

        EventListenerInterface eventListener = this._config.listener;
        if (parserResult.type.equal(Type.MODE)) {
            eventListener.onMode(parserResult.channel.name, parserResult.user);

            if (parserResult.message.getRaw().contains(" +o ")) {
                eventListener.onOp(parserResult.channel.name, parserResult.user);
            }
            if (parserResult.message.getRaw().contains(" -o ")) {
                eventListener.onDeop(parserResult.channel.name, parserResult.user);
            }
            return;
        }

        if (parserResult.type.equal(Type.JOIN)) {
            eventListener.onJoin(parserResult.channel.name, parserResult.user);
            return;
        }

        if (parserResult.type.equal(Type.PART)) {
            eventListener.onPart(parserResult.channel.name, parserResult.user);
            return;
        }

        if (parserResult.type.equal(Type.NOTICE)) {
            eventListener.onNotice(parserResult.channel.name, parserResult.message);
            return;
        }

        if (parserResult.type.equal(Type.PRIVMSG)) {
            eventListener.onIncomingMessage(parserResult.channel.name, parserResult.user, parserResult.message);
            return;
        }

        if (parserResult.type.equal(Type.WHISPER)) {
            eventListener.onWhisper(parserResult.user, parserResult.message);
            return;
        }

        if (parserResult.type.equal(Type.USERSTATE)) {
            eventListener.onUserstate(parserResult.channel.name, parserResult.user);
            return;
        }

        if (parserResult.type.equal(Type.CLEARCHAT)) {
            eventListener.onClearchat(parserResult.channel.name);
            return;
        }

        if (parserResult.type.equal(Type.HOSTTARGET)) {
            String[] messageSplit = parserResult.message.getMessage().split(" ");
            eventListener.onHostTarget(parserResult.channel.name, messageSplit[0], Integer.parseInt(messageSplit[1]));
            return;
        }

        if (parserResult.type.equal(Type.USERNOTICE)) {
            eventListener.onUsernotice(parserResult.channel.name, parserResult.message);

            if (parserResult.events != null) {
                eventListener.onEvents(parserResult.channel.name, parserResult.events);
            }
            return;
        }

        if (parserResult.type.equal(Type.ROOMSTATE)) {
            eventListener.onRoomstate(parserResult.channel.name);
            return;
        }

        eventListener.onUnknown(parserResult.channel.name, parserResult.user, parserResult.message);
    }
}
