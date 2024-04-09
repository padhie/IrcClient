package de.padhie.ircclient.Client;

import de.padhie.ircclient.Communication.Reader.NoopReader;
import de.padhie.ircclient.Communication.Writter.NoopWriter;
import de.padhie.ircclient.Configuration.Configuration;
import de.padhie.ircclient.EventListener.EventListenerInterface;
import de.padhie.ircclient.Logger.NoopLogger;
import de.padhie.ircclient.Message.Event.Events;
import de.padhie.ircclient.Message.MessageParser;
import de.padhie.ircclient.Message.Result;
import de.padhie.ircclient.Message.Type;
import Test.Helper;
import de.padhie.ircclient.Struct.Channel;
import de.padhie.ircclient.Struct.Message;
import de.padhie.ircclient.Struct.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private Client client;

    @BeforeEach
    void setUp () {
        this.createClient(new EventListenerTestClass());
    }

    @Test
    public void testTriggerEventWithPing () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onPing () {
                assertTrue(true);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.PING,
            new User(""),
            new Channel("", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithModeOp () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onMode (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
            public void onOp (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.MODE,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "#ThisIsARawMessage +o AndyRandomString"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithModeDeop () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onMode (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
            public void onDeop (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.MODE,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "#ThisIsARawMessage -o AndyRandomString"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithJoin () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onJoin (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.JOIN,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "#ThisIsAChannel"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithPart () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onPart (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.PART,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "#ThisIsAChannel"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithNotice () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onNotice (String channel, Message message) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("ThisIsARawMessage", message.getRaw());
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.NOTICE,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithPrivmsg () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onIncomingMessage (String channel, User user, Message message) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
                assertEquals("ThisIsARawMessage", message.getRaw());
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.PRIVMSG,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithWhisper () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onWhisper (User user, Message message) {
                assertEquals("RandomUser", user.name);
                assertEquals("ThisIsARawMessage", message.getRaw());
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.WHISPER,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithUserstate () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onUserstate (String channel, User user) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.USERSTATE,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithClearChat () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onClearchat (String channel) {
                assertEquals("#ThisIsAChannel", channel);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.CLEARCHAT,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithHosting () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onHostTarget (String channel, String raider, int amount) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("ThisIsARawMessage", raider);
                assertEquals(1234, amount);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.HOSTTARGET,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("ThisIsARawMessage 1234", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithUserNotice () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onUsernotice (String channel, Message message) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("ThisIsARawMessage", message.getRaw());
            }
            public void onEvents (String channel, Events events) {
                assertEquals("#ThisIsAChannel", channel);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.USERNOTICE,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithRoomState () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onRoomstate (String channel) {
                assertEquals("#ThisIsAChannel", channel);
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.ROOMSTATE,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    @Test
    public void testTriggerEventWithUnknown () throws InvocationTargetException, IllegalAccessException {

        EventListenerInterface listener = new EventListenerTestClass() {
            public void onUnknown (String channel, User user, Message message) {
                assertEquals("#ThisIsAChannel", channel);
                assertEquals("RandomUser", user.name);
                assertEquals("ThisIsARawMessage", message.getRaw());
            }
        };
        this.createClient(listener);

        Result parserResult = new Result(
            "",
            Type.UNKNOWN,
            new User("RandomUser"),
            new Channel("#ThisIsAChannel", ""),
            new Message("", "ThisIsARawMessage"),
            new Events()
        );

        Helper.getProtectedMethod(this.client, "triggerEvent", Result.class).invoke(this.client, parserResult);
    }

    private void createClient (EventListenerInterface listener) {
        Configuration config = new Configuration(
            false,
            "chatset",
            "username",
            "password",
            "channel",
            "server",
            0,
            listener,
            new NoopReader(),
            new NoopWriter(),
            new MessageParser(),
            new NoopLogger(),
            new String[0],
            new String[0]
        );

        this.client = new Client(config);
    }

    private static class EventListenerTestClass implements EventListenerInterface {
        public void setHandler (Client handler) {}

        public void setConfig (Configuration configuration) {}

        public void onPing () {
            fail("onPing - method not allowed to call");
        }

        public void onConnect () {
            fail("onConnect - method not allowed to call");
        }

        public void onLogin (User user) {
            fail("onLogin - method not allowed to call");
        }

        public void onLeave (String channel) {
            fail("onLeave - method not allowed to call");
        }

        public void onDisconnect (String channel) {
            fail("onDisconnect - method not allowed to call");
        }

        public void onJoin (String channel, User user) {
            fail("onJoin - method not allowed to call");
        }

        public void onPart (String channel, User user) {
            fail("onPart - method not allowed to call");
        }

        public void onSendRawLine (String line) {
            fail("onSendRawLine - method not allowed to call");
        }

        public void onIncomingMessage (String channel, User user, Message message) {
            fail("onIncomingMessage - method not allowed to call");
        }

        public void onWhisper (User user, Message message) {
            fail("onWhisper - method not allowed to call");
        }

        public void onOutgoingMessage (String channel, User user, Message message) {
            fail("onOutgoingMessage - method not allowed to call");
        }

        public void onOp (String channel, User user) {
            fail("onOp - method not allowed to call");
        }

        public void onDeop (String channel, User user) {
            fail("onDeop - method not allowed to call");
        }

        public void onMode (String channel, User user) {
            fail("onMode - method not allowed to call");
        }

        public void onNotice (String channel, Message message) {
            fail("onNotice - method not allowed to call");
        }

        public void onUserstate (String channel, User user) {
            fail("onUserstate - method not allowed to call");
        }

        public void onClearchat (String channel) {
            fail("onClearchat - method not allowed to call");
        }

        public void onHostTarget (String channel, String target, int amount) {
            fail("onHostTarget - method not allowed to call");
        }

        public void onUsernotice (String channel, Message message) {
            fail("onUsernotice - method not allowed to call");
        }

        public void onEvents(String channel, Events events) {
            fail("onEvents - method not allowed to call");
        }

        public void onRoomstate (String channel) {
            fail("onRoomstate - method not allowed to call");
        }

        public void onUnknown (String channel, User user, Message message) {
            fail("onUnknown - method not allowed to call");
        }

        public void onError(String method, Throwable exception) {
            fail("onError - method not allowed to call");
        }
    }
}