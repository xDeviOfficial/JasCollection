package pl.jasmc.jashub.objects;

import java.util.HashMap;
import java.util.Map;

public class MetaStorage {

    private static Map<String, PlayerMeta> storage = new HashMap<>();


    public static void storeMeta(String name) {
        PlayerMeta meta = new PlayerMeta(name);
        storage.put(meta.getName(), meta);
    }

    public static void deleteMeta(String name) {
        storage.remove(name);
    }

    public static PlayerMeta getPlayerMeta(String name) {
        if(storage.containsKey(name)) {
            return storage.get(name);
        } else {
            return new PlayerMeta(name);
        }
    }

}
