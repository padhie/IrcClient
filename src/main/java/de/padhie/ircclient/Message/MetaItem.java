package de.padhie.ircclient.Message;

public class MetaItem {
    public final String key;
    public final String value;

    public MetaItem (String key, String value) {
        this.key = key;
        this.value = value;
    }

    public boolean equals(MetaItem item) {
        return this.key.equals(item.key)
            && this.value.equals(item.value);
    }
}
