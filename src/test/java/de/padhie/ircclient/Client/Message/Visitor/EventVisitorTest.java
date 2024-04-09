package de.padhie.ircclient.Client.Message.Visitor;

import de.padhie.ircclient.Message.Visitor.EventVisitor;
import Test.Helper;
import de.padhie.ircclient.Message.Event.SubPlan;
import de.padhie.ircclient.Message.MessageBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventVisitorTest {
    @Test
    public void testVisitWithMe () {
        String raw = Helper.loadFixture("Model/line_normal-user-with-action.txt");
        MessageBuilder messageBuilder = new MessageBuilder(raw);

        (new EventVisitor()).visit(messageBuilder);

        assertNotNull(messageBuilder.events);
        assertNotNull(messageBuilder.events.me);
        assertEquals("I am a user who has emphasized his message.", messageBuilder.events.me.message);
    }

    @Test
    public void testVisitWithRaid () {
        String raw = Helper.loadFixture("Model/line_raid.txt");
        MessageBuilder messageBuilder = new MessageBuilder(raw);

        (new EventVisitor()).visit(messageBuilder);

        assertNotNull(messageBuilder.events);
        assertNotNull(messageBuilder.events.raid);
        assertEquals("padhiebot", messageBuilder.events.raid.raider);
        assertEquals(999, messageBuilder.events.raid.viewerCount);
        // assertEquals("999\sraiders\sfrom\sWillowbptDebugTester\shave\sjoined", messageBuilder.events.raid.message);
    }

    @Test
    public void testVisitWithPrimeSub () {
        String raw = Helper.loadFixture("Model/line_prime-sub.txt");
        MessageBuilder messageBuilder = new MessageBuilder(raw);

        (new EventVisitor()).visit(messageBuilder);

        assertNotNull(messageBuilder.events);
        assertNotNull(messageBuilder.events.sub);
        assertEquals("padhiebot", messageBuilder.events.sub.user);
        assertEquals(SubPlan.PRIME, messageBuilder.events.sub.plan);
        assertFalse(messageBuilder.events.sub.gifted);
        assertEquals(0, messageBuilder.events.sub.month);
        assertEquals(19, messageBuilder.events.sub.cumulativeMonth);
        assertEquals(0, messageBuilder.events.sub.multimonthDuration);
        assertEquals(0, messageBuilder.events.sub.multimonthTenure);
        assertEquals(0, messageBuilder.events.sub.shouldShareStreak);
    }

    @Test
    public void testVisitWithAnonymousGift () {
        String raw = Helper.loadFixture("Model/line_anonymous-gift.txt");
        MessageBuilder messageBuilder = new MessageBuilder(raw);

        (new EventVisitor()).visit(messageBuilder);

        assertNotNull(messageBuilder.events);
        assertNotNull(messageBuilder.events.gift);
        assertEquals("ananonymousgifter", messageBuilder.events.gift.user);
        assertEquals("WillowbotGiftRecipient", messageBuilder.events.gift.recipient);
        assertEquals(SubPlan.TIES1, messageBuilder.events.gift.plan);
        assertEquals(1, messageBuilder.events.gift.month);
        assertEquals(1, messageBuilder.events.gift.giftedMonth);
    }
}
