package de.padhie.ircclient.Client.Message;

import de.padhie.ircclient.Message.MessageParser;
import de.padhie.ircclient.Message.Result;
import de.padhie.ircclient.Message.Type;
import Test.Helper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @see <a href="https://github.com/weidengeist/Willowbot/blob/main/modules/debug.py">message sample (powered by Weidengeist)</a>
 */
public class MessageParserTest {
    @Test
    public void testResultWithActionMessage () {
        String raw = Helper.loadFixture("Model/line_normal-user-with-action.txt");
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("padhiebot", result.user.name);
        assertEquals("subscriber/56", result.user.getParamWithDefault("badge-info").value);
        assertEquals("moderator/1,subscriber/3054", result.user.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("", result.user.getParamWithDefault("emotes").value);
        assertEquals("0", result.user.getParamWithDefault("first-msg").value);
        assertEquals("", result.user.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("id").value);
        assertEquals("1", result.user.getParamWithDefault("mod").value);
        assertEquals("0", result.user.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.user.getParamWithDefault("room-id").value);
        assertEquals("1", result.user.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("mod", result.user.getParamWithDefault("user-type").value);

        assertEquals("I am a user who has emphasized his message.", result.message.getMessage());
        assertTrue(result.message.isAction());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/56", result.message.getParamWithDefault("badge-info").value);
        assertEquals("moderator/1,subscriber/3054", result.message.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("0", result.message.getParamWithDefault("first-msg").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("1", result.message.getParamWithDefault("mod").value);
        assertEquals("0", result.message.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("mod", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithAnnouncement () {
        String raw = "@badge-info=subscriber/56;badges=moderator/1,subscriber/36,artist-badge/1;color=#FF0000;display-name=padhiebot;emotes=;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;login=padhiebot;mod=1;msg-id=announcement;msg-param-color=#FF0000;room-id=123456789;subscriber=1;system-msg=;tmi-sent-ts=1658754900000;user-id=98765432;user-type=mod :tmi.twitch.tv USERNOTICE #padhiebot :Attention everyone! This is an announcement.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("Attention everyone! This is an announcement.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/56", result.message.getParamWithDefault("badge-info").value);
        assertEquals("moderator/1,subscriber/36,artist-badge/1", result.message.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("1", result.message.getParamWithDefault("mod").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("mod", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithBan () {
        String raw = "@room-id=123456789;target-user-id=98765432;tmi-sent-ts=1658754900000 :tmi.twitch.tv CLEARCHAT #padhiebot :iamthebanneduser";
        Result result = (new MessageParser()).parse(raw);

        assertEquals("#padhiebot", result.channel.name);
    }

    @Test
    public void testResultWithDeleteMessage () {
        String raw = "@login=ilikespoilingsecrets;room-id=;target-msg-id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;tmi-sent-ts=1658754900000 :tmi.twitch.tv CLEARMSG #padhiebot :This is a message that has been deleted by someone with moderator privileges.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.UNKNOWN, result.type);
        // twitch not able to have line without channel
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("ilikespoilingsecrets", result.user.getParamWithDefault("login").value);
        assertEquals("", result.user.getParamWithDefault("room-id").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("target-msg-id").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);

        assertEquals("This is a message that has been deleted by someone with moderator privileges.", result.message.getMessage());
        assertEquals("ilikespoilingsecrets", result.message.getParamWithDefault("login").value);
        assertEquals("", result.message.getParamWithDefault("room-id").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("target-msg-id").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
    }

    @Test
    public void testResultWithModUserMessage () {
        String raw = "@badge-info=subscriber/56;badges=moderator/1,subscriber/2054;color=#FF0000;display-name=padhiebot;emotes=;first-msg=0;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;mod=1;returning-chatter=0;room-id=123456789;subscriber=1;tmi-sent-ts=1658754900000;turbo=0;user-id=98765432;user-type=mod :padhiebot!padhiebot@padhiebot.tmi.twitch.tv PRIVMSG #padhiebot :I am a moderator of this channel and posted this.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("padhiebot", result.user.name);
        assertTrue(result.user.isMod());
        assertEquals("subscriber/56", result.user.getParamWithDefault("badge-info").value);
        assertEquals("moderator/1,subscriber/2054", result.user.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("", result.user.getParamWithDefault("emotes").value);
        assertEquals("0", result.user.getParamWithDefault("first-msg").value);
        assertEquals("", result.user.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("id").value);
        assertEquals("1", result.user.getParamWithDefault("mod").value);
        assertEquals("0", result.user.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.user.getParamWithDefault("room-id").value);
        assertEquals("1", result.user.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("mod", result.user.getParamWithDefault("user-type").value);

        assertEquals("I am a moderator of this channel and posted this.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/56", result.message.getParamWithDefault("badge-info").value);
        assertEquals("moderator/1,subscriber/2054", result.message.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("0", result.message.getParamWithDefault("first-msg").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("1", result.message.getParamWithDefault("mod").value);
        assertEquals("0", result.message.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("mod", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithNormalUserMessage () {
        String raw = Helper.loadFixture("Model/line_normal-user.txt");
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("padhiebot", result.user.name);
        assertEquals("", result.user.getParamWithDefault("badge-info").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.user.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("555555560:83-84/555555584:114-115/1:31-32", result.user.getParamWithDefault("emotes").value);
        assertEquals("0", result.user.getParamWithDefault("first-msg").value);
        assertEquals("", result.user.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("id").value);
        assertEquals("0", result.user.getParamWithDefault("mod").value);
        assertEquals("0", result.user.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.user.getParamWithDefault("room-id").value);
        assertEquals("0", result.user.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("", result.user.getParamWithDefault("user-type").value);

        assertEquals("This is a message from an non-sub user, not posting for the very first time.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("", result.message.getParamWithDefault("badge-info").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.message.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("555555560:83-84/555555584:114-115/1:31-32", result.message.getParamWithDefault("emotes").value);
        assertEquals("0", result.message.getParamWithDefault("first-msg").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("0", result.message.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("0", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithNormalUserMessageFirstPost () {
        String raw = "@badge-info=;badges=;client-nonce=9i8h7g6f5e4d3c2b1a;color=#FF0000;display-name=padhiebot;emotes=;first-msg=1;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;mod=0;returning-chatter=0;room-id=123456789;subscriber=0;tmi-sent-ts=1658754900000;turbo=0;user-id=98765432;user-type= :padhiebot!padhiebot@padhiebot.tmi.twitch.tv PRIVMSG #padhiebot :This is a message from an non-sub user, posting for the very first time.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("#padhiebot", result.channel.name);
        
        assertEquals("padhiebot", result.user.name);
        assertEquals("", result.user.getParamWithDefault("badge-info").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.user.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("", result.user.getParamWithDefault("emotes").value);
        assertEquals("1", result.user.getParamWithDefault("first-msg").value);
        assertEquals("", result.user.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("id").value);
        assertEquals("0", result.user.getParamWithDefault("mod").value);
        assertEquals("0", result.user.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.user.getParamWithDefault("room-id").value);
        assertEquals("0", result.user.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("", result.user.getParamWithDefault("user-type").value);

        assertEquals("This is a message from an non-sub user, posting for the very first time.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("", result.message.getParamWithDefault("badge-info").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.message.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("1", result.message.getParamWithDefault("first-msg").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("0", result.message.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("0", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithSubUserMessage () {
        String raw = "@badge-info=subscriber/40;badges=subscriber/36,sub-gifter/50;client-nonce=9i8h7g6f5e4d3c2b1a;color=#FF0000;display-name=padhiebot;emotes=;first-msg=0;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;mod=0;returning-chatter=0;room-id=123456789;subscriber=1;tmi-sent-ts=1658754900000;turbo=0;user-id=98765432;user-type= :padhiebot!padhiebot@padhiebot.tmi.twitch.tv PRIVMSG #padhiebot :This is a message from a subscriber.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("#padhiebot", result.channel.name);
        
        assertEquals("padhiebot", result.user.name);
        assertTrue(result.user.isSub());
        assertEquals("subscriber/40", result.user.getParamWithDefault("badge-info").value);
        assertEquals("subscriber/36,sub-gifter/50", result.user.getParamWithDefault("badges").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.user.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("", result.user.getParamWithDefault("emotes").value);
        assertEquals("0", result.user.getParamWithDefault("first-msg").value);
        assertEquals("", result.user.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("id").value);
        assertEquals("0", result.user.getParamWithDefault("mod").value);
        assertEquals("0", result.user.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.user.getParamWithDefault("room-id").value);
        assertEquals("1", result.user.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("", result.user.getParamWithDefault("user-type").value);

        assertEquals("This is a message from a subscriber.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/40", result.message.getParamWithDefault("badge-info").value);
        assertEquals("subscriber/36,sub-gifter/50", result.message.getParamWithDefault("badges").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.message.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("0", result.message.getParamWithDefault("first-msg").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("0", result.message.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithVipUserMessage () {
        String raw = "@badge-info=;badges=;client-nonce=9i8h7g6f5e4d3c2b1a;color=#FF0000;display-name=padhiebot;emotes=;first-msg=0;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;mod=0;returning-chatter=0;room-id=123456789;subscriber=0;tmi-sent-ts=1658754900000;turbo=0;user-id=98765432;user-type=;vip=1 :padhiebot!padhiebot@padhiebot.tmi.twitch.tv PRIVMSG #padhiebot :This is a message from an VIP user.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PRIVMSG, result.type);
        assertEquals("#padhiebot", result.channel.name);
        
        assertEquals("padhiebot", result.user.name);
        assertTrue(result.user.isVip());
        assertEquals("", result.user.getParamWithDefault("badge-info").value);
        assertEquals("", result.user.getParamWithDefault("badges").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.user.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("", result.user.getParamWithDefault("emotes").value);
        assertEquals("0", result.user.getParamWithDefault("first-msg").value);
        assertEquals("", result.user.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.user.getParamWithDefault("id").value);
        assertEquals("0", result.user.getParamWithDefault("mod").value);
        assertEquals("0", result.user.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.user.getParamWithDefault("room-id").value);
        assertEquals("0", result.user.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.user.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("", result.user.getParamWithDefault("user-type").value);
        assertEquals("1", result.user.getParamWithDefault("vip").value);

        assertEquals("This is a message from an VIP user.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("", result.message.getParamWithDefault("badge-info").value);
        assertEquals("", result.message.getParamWithDefault("badges").value);
        assertEquals("9i8h7g6f5e4d3c2b1a", result.message.getParamWithDefault("client-nonce").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("0", result.message.getParamWithDefault("first-msg").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("0", result.message.getParamWithDefault("returning-chatter").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("0", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
        assertEquals("1", result.message.getParamWithDefault("vip").value);
    }

    @Test
    public void testResultWithRaid () {
        String raw = Helper.loadFixture("Model/line_raid.txt");
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("", result.message.getParamWithDefault("badge-info").value);
        assertEquals("", result.message.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("login").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("raid", result.message.getParamWithDefault("msg-id").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("msg-param-displayName").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("msg-param-login").value);
        assertEquals("https://static-cdn.jtvnw.net/jtv_user_pictures/this-url-leads-nowhere-profile_image-70x70.png", result.message.getParamWithDefault("msg-param-profileImageURL").value);
        assertEquals("999", result.message.getParamWithDefault("msg-param-viewerCount").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("0", result.message.getParamWithDefault("subscriber").value);
        // assertEquals("999\sraiders\sfrom\sWillowbptDebugTester\shave\sjoined", result.message.getParamWithDefault("system-msg").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithPrimeSub () {
        String raw = Helper.loadFixture("Model/line_prime-sub.txt");
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("I have subscribed with Prime and added this message.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/99", result.message.getParamWithDefault("badge-info").value);
        assertEquals("subscriber/18", result.message.getParamWithDefault("badges").value);
        assertEquals("", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("login").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("resub", result.message.getParamWithDefault("msg-id").value);
        assertEquals("19", result.message.getParamWithDefault("msg-param-cumulative-months").value);
        assertEquals("0", result.message.getParamWithDefault("msg-param-months").value);
        assertEquals("0", result.message.getParamWithDefault("msg-param-multimonth-duration").value);
        assertEquals("0", result.message.getParamWithDefault("msg-param-multimonth-tenure").value);
        assertEquals("0", result.message.getParamWithDefault("msg-param-should-share-streak").value);
        // assertEquals("A\ssubscription\sname\sfor\sWillowbot\sDebug\sTester", result.message.getParamWithDefault("msg-param-sub-plan-name").value);
        assertEquals("Prime", result.message.getParamWithDefault("msg-param-sub-plan").value);
        assertEquals("false", result.message.getParamWithDefault("msg-param-was-gifted").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithAnonymousSubGift () {
        String raw = Helper.loadFixture("Model/line_anonymous-gift.txt");
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("", result.message.getParamWithDefault("badge-info").value);
        assertEquals("", result.message.getParamWithDefault("badges").value);
        assertEquals("", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("ananonymousgifter", result.message.getParamWithDefault("login").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("subgift", result.message.getParamWithDefault("msg-id").value);
        assertEquals("FunStringFour", result.message.getParamWithDefault("msg-param-fun-string").value);
        assertEquals("1", result.message.getParamWithDefault("msg-param-gift-months").value);
        assertEquals("1", result.message.getParamWithDefault("msg-param-months").value);
        // assertEquals("this\sis\san\sorigin\sID", result.message.getParamWithDefault("msg-param-origin-id").value);
        assertEquals("WillowbotGiftRecipient", result.message.getParamWithDefault("msg-param-recipient-display-name").value);
        assertEquals("12345678", result.message.getParamWithDefault("msg-param-recipient-id").value);
        assertEquals("willowbotgiftrecipient", result.message.getParamWithDefault("msg-param-recipient-user-name").value);
        // assertEquals("A\ssubscription\sname\sfor\sWillowbot\sDebug\sTester", result.message.getParamWithDefault("msg-param-sub-plan-name").value);
        assertEquals("1000", result.message.getParamWithDefault("msg-param-sub-plan").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("0", result.message.getParamWithDefault("subscriber").value);
        // assertEquals("An\sanonymous\suser\sgifted\sa\sTier\s1\ssub\sto\spadhiebot!\s", result.message.getParamWithDefault("system-msg").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithMultiSubGift () {
        String raw = "@badge-info=subscriber/20;badges=subscriber/18,sub-gifter/10;color=#652669;display-name=padhiebot;emotes=;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;login=padhiebot;mod=0;msg-id=submysterygift;msg-param-mass-gift-count=10;msg-param-origin-id=this\\sis\\san\\sorigin\\sID;msg-param-sender-count=50;msg-param-sub-plan=1000;room-id=123456789;subscriber=1;system-msg=padhiebot\\sis\\sgifting\\s10\\sTier\\s1\\sSubs\\sto\\spadhiebot\\'s\\scommunity!\\sThey\\'ve\\sgifted\\sa\\stotal\\sof\\s50\\sin\\sthe\\schannel!;tmi-sent-ts=1658754900000;user-id=98765432;user-type= :tmi.twitch.tv USERNOTICE #padhiebot";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/20", result.message.getParamWithDefault("badge-info").value);
        assertEquals("subscriber/18,sub-gifter/10", result.message.getParamWithDefault("badges").value);
        assertEquals("#652669", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("login").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("10", result.message.getParamWithDefault("msg-param-mass-gift-count").value);
        // assertEquals("this\sis\san\sorigin\sID", result.message.getParamWithDefault("msg-param-origin-id").value);
        assertEquals("50", result.message.getParamWithDefault("msg-param-sender-count").value);
        assertEquals("1000", result.message.getParamWithDefault("msg-param-sub-plan").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        // assertEquals("padhiebot\sis\sgifting\s10\sTier\s1\sSubs\sto\spadhiebot\'s\scommunity!\sThey\'ve\sgifted\sa\stotal\sof\s50\sin\sthe\schannel!", result.message.getParamWithDefault("system-msg").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithSingleSubGiftFollowUp () {
        String raw = "@badge-info=subscriber/20;badges=subscriber/18,sub-gifter/10;color=#652669;display-name=padhiebot;emotes=;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;login=padhiebot;mod=0;msg-id=subgift;msg-param-gift-months=1;msg-param-months=3;msg-param-origin-id=this\\sis\\san\\sorigin\\sID;msg-param-recipient-display-name=WillowbotGiftRecipient;msg-param-recipient-id=123456789;msg-param-recipient-user-name=willowbotgiftrecipient;msg-param-sender-count=0;msg-param-sub-plan-name=A\\ssubscription\\sname\\sfor\\sWillowbot\\sDebug\\sTester;msg-param-sub-plan=1000;room-id=123456789;subscriber=1;system-msg=padhiebot\\sgifted\\sa\\sTier\\s1\\ssub\\sto\\sWillowbotGiftRecipient!;tmi-sent-ts=1658754900000;user-id=98765432;user-type= :tmi.twitch.tv USERNOTICE #padhiebot";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/20", result.message.getParamWithDefault("badge-info").value);
        assertEquals("subscriber/18,sub-gifter/10", result.message.getParamWithDefault("badges").value);
        assertEquals("#652669", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("login").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("subgift", result.message.getParamWithDefault("msg-id").value);
        assertEquals("1", result.message.getParamWithDefault("msg-param-gift-months").value);
        assertEquals("3", result.message.getParamWithDefault("msg-param-months").value);
        // assertEquals("this\sis\san\sorigin\sID", result.message.getParamWithDefault("msg-param-origin-id").value);
        assertEquals("WillowbotGiftRecipient", result.message.getParamWithDefault("msg-param-recipient-display-name").value);
        assertEquals("123456789", result.message.getParamWithDefault("msg-param-recipient-id").value);
        assertEquals("willowbotgiftrecipient", result.message.getParamWithDefault("msg-param-recipient-user-name").value);
        assertEquals("0", result.message.getParamWithDefault("msg-param-sender-count").value);
        // assertEquals("A\ssubscription\sname\sfor\sWillowbot\sDebug\sTester", result.message.getParamWithDefault("msg-param-sub-plan-name").value);
        assertEquals("1000", result.message.getParamWithDefault("msg-param-sub-plan").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithSingleSubGiftTotalRecipient () {
        String raw = "@badge-info=subscriber/25;badges=subscriber/2024,bits/25000;color=#FF0000;display-name=padhiebot;emotes=;flags=;id=12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z;login=padhiebot;mod=0;msg-id=subgift;msg-param-gift-months=1;msg-param-months=35;msg-param-origin-id=this\\sis\\san\\sorigin\\sID;msg-param-recipient-display-name=WillowbotGiftRecipient;msg-param-recipient-id=74734078;msg-param-recipient-user-name=willowbotgiftrecipient;msg-param-sender-count=50;msg-param-sub-plan-name=A\\ssubscription\\sname\\sfor\\sWillowbot\\sDebug\\sTester;msg-param-sub-plan=1000;room-id=123456789;subscriber=1;system-msg=padhiebot\\sgifted\\sa\\sTier\\s1\\ssub\\sto\\sWillowbotGiftRecipient!\\sThey\\shave\\sgiven\\s50\\sGift\\sSubs\\sin\\sthe\\schannel!;tmi-sent-ts=1658754900000;user-id=98765432;user-type= :tmi.twitch.tv USERNOTICE #padhiebot";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.USERNOTICE, result.type);
        assertEquals("#padhiebot", result.channel.name);

        assertEquals("", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("subscriber/25", result.message.getParamWithDefault("badge-info").value);
        assertEquals("subscriber/2024,bits/25000", result.message.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("", result.message.getParamWithDefault("flags").value);
        assertEquals("12345msgID54321-98765msgID56789-123-4u5v6w7x8y9z", result.message.getParamWithDefault("id").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("login").value);
        assertEquals("0", result.message.getParamWithDefault("mod").value);
        assertEquals("subgift", result.message.getParamWithDefault("msg-id").value);
        assertEquals("1", result.message.getParamWithDefault("msg-param-gift-months").value);
        assertEquals("35", result.message.getParamWithDefault("msg-param-months").value);
        // assertEquals("this\sis\san\sorigin\sID", result.message.getParamWithDefault("msg-param-origin-id").value);
        assertEquals("WillowbotGiftRecipient", result.message.getParamWithDefault("msg-param-recipient-display-name").value);
        assertEquals("74734078", result.message.getParamWithDefault("msg-param-recipient-id").value);
        assertEquals("willowbotgiftrecipient", result.message.getParamWithDefault("msg-param-recipient-user-name").value);
        assertEquals("50", result.message.getParamWithDefault("msg-param-sender-count").value);
        // assertEquals("A\ssubscription\sname\sfor\sWillowbot\sDebug\sTester", result.message.getParamWithDefault("msg-param-sub-plan-name").value);
        assertEquals("1000", result.message.getParamWithDefault("msg-param-sub-plan").value);
        assertEquals("123456789", result.message.getParamWithDefault("room-id").value);
        assertEquals("1", result.message.getParamWithDefault("subscriber").value);
        assertEquals("1658754900000", result.message.getParamWithDefault("tmi-sent-ts").value);
        // assertEquals("padhiebot\sgifted\sa\sTier\s1\ssub\sto\sWillowbotGiftRecipient!\sThey\shave\sgiven\s50\sGift\sSubs\sin\sthe\schannel!", result.message.getParamWithDefault("system-msg").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithWhisper () {
        String raw = "@badges=glitchcon2020/1;color=#FF0000;display-name=padhiebot;emotes=;message-id=1;thread-id=12345678_987654321;turbo=0;user-id=98765432;user-type= :padhiebot!padhiebot@padhiebot.tmi.twitch.tv WHISPER willowbotwhisperrecipient :This is a whisper message from padhiebot to WillowbotWhisperRecipient.";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.WHISPER, result.type);
        assertEquals("willowbotwhisperrecipient", result.channel.name);

        assertEquals("padhiebot", result.user.name);
        assertEquals("glitchcon2020/1", result.user.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.user.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.user.getParamWithDefault("display-name").value);
        assertEquals("", result.user.getParamWithDefault("emotes").value);
        assertEquals("1", result.user.getParamWithDefault("message-id").value);
        assertEquals("12345678_987654321", result.user.getParamWithDefault("thread-id").value);
        assertEquals("0", result.user.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.user.getParamWithDefault("user-id").value);
        assertEquals("", result.user.getParamWithDefault("user-type").value);

        assertEquals("This is a whisper message from padhiebot to WillowbotWhisperRecipient.", result.message.getMessage());
        assertEquals(raw, result.message.getRaw());
        assertEquals("glitchcon2020/1", result.message.getParamWithDefault("badges").value);
        assertEquals("#FF0000", result.message.getParamWithDefault("color").value);
        assertEquals("padhiebot", result.message.getParamWithDefault("display-name").value);
        assertEquals("", result.message.getParamWithDefault("emotes").value);
        assertEquals("1", result.message.getParamWithDefault("message-id").value);
        assertEquals("12345678_987654321", result.message.getParamWithDefault("thread-id").value);
        assertEquals("0", result.message.getParamWithDefault("turbo").value);
        assertEquals("98765432", result.message.getParamWithDefault("user-id").value);
        assertEquals("", result.message.getParamWithDefault("user-type").value);
    }

    @Test
    public void testResultWithPing () {
        String raw = "PING :tmi.twitch.tv";
        Result result = (new MessageParser()).parse(raw);

        assertEquals(Type.PING, result.type);
    }
}
