package uiuc.cs411.nozama.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing content for user interfaces
 * 
 */
public class Content {

    /**
     * An array of sample items.
     */
    public static List<Item> ITEMS = new ArrayList<Item>();

    /**
     * A map of items, by ID.
     */
    public static Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

    static {
        // Add 3 sample items.
        addItem(new Item("1", "Create a Post"));
        addItem(new Item("2", "Find Posts"));
        addItem(new Item("3", "My Posts"));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * An item representing a piece of content.
     */
    public static class Item {
        public String id;
        public String content;

        public Item(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
