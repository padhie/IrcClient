package de.padhie.ircclient.Client.Message;

import de.padhie.ircclient.Message.MetaItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetaItemTest {
    @Test
    public void testEquals () {
        MetaItem baseItem = new MetaItem("foo", "bar");
        MetaItem compareItemOne = new MetaItem("foo", "bar");
        MetaItem compareItemTwo = new MetaItem("bar", "foo");

        assertTrue(baseItem.equals(compareItemOne));
        assertFalse(baseItem.equals(compareItemTwo));
    }
}
