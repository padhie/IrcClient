package de.padhie.ircclient.Client;

import de.padhie.ircclient.EventListener.EventListenerInterface;
import de.padhie.ircclient.Configuration.Configuration;
import de.padhie.ircclient.Message.Result;
import de.padhie.ircclient.Message.Type;
import de.padhie.ircclient.Struct.User;

import java.io.IOException;
import java.net.Socket;

public class Client implements ClientInterface{
    private final Configuration config;

    public Client (Configuration config) {
        this.config = config;
        this.config.listener.setHandler(this);
    }

    public boolean login () {
        try {
            @SuppressWarnings ("resource") Socket socket = new Socket(this.config.server, this.config.port);
            this.config.writer.init(socket);
            this.config.reader.init(socket);
        } catch (IOException exception) {
            this.config.listener.onError("login-connect", exception);

            return false;
        }

        if (!this.config.password.isEmpty()) {
            this.config.logger.addLog("using pass: ***");
            this.config.writer.writeLine("PASS " + this.config.password);
        }

        User loggedInUser = new User("");
        if (!this.config.username.isEmpty()) {
            loggedInUser = new User(this.config.username);
            
            this.config.logger.addLog("using nick: " + this.config.username);
            this.config.writer.writeLine("NICK " + this.config.username);
            
            this.config.logger.addLog("using login: " + this.config.username);
            this.config.writer.writeLine("USER " + this.config.username + " 8 * : Java IRC IrcPLib Client");
        }

        // Read lines from the server until it tells us we have connected.
        String line;
        while ((line = this.config.reader.readLine()) != null) {
            if (line.contains("004")) {
                // We are now logged in.
                this.config.logger.addLog("Login success.");
                this.config.listener.onLogin(loggedInUser);
                break;
            } else if (line.contains("433")) {
                this.config.logger.addLog("Nickname is already in use.");
                return false;
            }
        }

        return true;
    }

    public void join () {
        String channel = this.config.channel;
        if (channel.isEmpty()) {
            return;
        }

        for (String preLine : this.config.preLines) {
            this.config.writer.writeLine(preLine);
        }

        this.config.writer.writeLine("JOIN " + channel);

        for (String postLine : this.config.postLines) {
            this.config.writer.writeLine(postLine);
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
        String channel = this.config.channel;

        this.config.writer.writeLine("PART " + channel);
        this.config.listener.onLeave(channel);
    }

    private void _start () {
        String line;

        // Keep reading lines from the server.
        while ((line = this.config.reader.readLine()) != null) {
            this.config.logger.addLog(line);
            if (!line.startsWith(":tmi.")) {
                Result parserResult = this.config.messageParser.parse(line);
                this.triggerEvent(parserResult);
            }
        }
    }

    private void triggerEvent (Result parserResult) {
        if (parserResult.type.equal(Type.PING)) {
            this.config.writer.writeLine("PONG " + parserResult.message.getRaw().substring(5));
            this.config.listener.onPing();
            return;
        }

        EventListenerInterface eventListener = this.config.listener;
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
