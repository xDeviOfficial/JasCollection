package pl.jasmc.jashub.objects;

import org.bukkit.inventory.ItemStack;
import pl.jasmc.jashub.JasCollection;

import java.util.HashMap;
import java.util.Map;

public class CollectionStorage {

    public static Map<Integer, CollectionItem> collection = new HashMap<>();

    public static void loadCollection() {
        for (String name : JasCollection.getItemBase().getCfg().getConfigurationSection("").getKeys(false)) {
            CollectionItem item = new CollectionItem(JasCollection.getItemBase().getCfg().getItemStack(name+ ".itemstack"), JasCollection.getItemBase().getCfg().getInt(name + ".id"), JasCollection.getItemBase().getCfg().getInt(name + ".price"), name);
            collection.put(item.getId(), item);
        }
    }

    public static CollectionItem getItemByID(int id) {
        for(CollectionItem item : collection.values()) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public static Map<Integer, CollectionItem> getCollectionItems() {
        return collection;
    }




}
