package IrcClient.Message;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MetaCollection extends ArrayList<MetaItem> {
    public @Nullable MetaItem get (String key) {
        for (Object o : this.toArray()) {
            MetaItem item = (MetaItem) o;

            if (item.key.equalsIgnoreCase(key)) {
                return item;
            }
        }

        return null;
    }

    public boolean has (String key) {
        for (Object o : this.toArray()) {
            MetaItem item = (MetaItem) o;

            if (item.key.equalsIgnoreCase(key)) {
                return true;
            }
        }

        return false;
    }

    public boolean equals (MetaCollection collection) {
        if (this.size() != collection.size()) {
            return false;
        }

        for (MetaItem item : this) {
            MetaItem compareItem = collection.get(item.key);
            if (compareItem == null) {
                return false;
            }

            if (!item.equals(compareItem)) {
                return false;
            }
        }

        return true;
    }
}
