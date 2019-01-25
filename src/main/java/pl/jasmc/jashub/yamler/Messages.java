package pl.jasmc.jashub.yamler;

import org.bukkit.ChatColor;
import pl.jasmc.jashub.JasCollection;

import java.io.File;

public class Messages {

    private Yamler yamler;

    public static String UNLOCKED;
    public static String ITEM_ON;
    public static String ITEMS_OFF;
    public static String NOT_ENOUGH_MONEY;
    public static String RANDOM_ITEM;
    public static String RANDOM_ITEM_MONEY;

    public void Messages() {

    }

    public void create() {
        UNLOCKED = "&a&lJasMC » &bPomyslnie odblokowano &a {item}";
        ITEM_ON = "&a&lJasMC » &bZalozyles &a{item}";
        ITEMS_OFF = "&a&lJasMC » &bZbroja zostala zdjeta.";
        NOT_ENOUGH_MONEY = "&a&lJasMC » &cNie masz wustarczająco zlotego pylu Potrzeba: {needed}, posiadasz: {coins}";
        RANDOM_ITEM = "&a&lJasMC » &bWylosowales &a{item}";
        RANDOM_ITEM_MONEY = "&a&lJasMC » &bWylosowales &a{randomCoins}";
        File messages = new File(JasCollection.getInstance().getDataFolder(), "messages.yml");
        if(!messages.exists()) {
            yamler = new Yamler(messages);
            yamler.getCfg().addDefault("Messages.ITEM_UNLOCKED", UNLOCKED);
            yamler.getCfg().addDefault("Messages.ITEM_ON", ITEM_ON);
            yamler.getCfg().addDefault("Messages.ITEMS_OFF", ITEMS_OFF);
            yamler.getCfg().addDefault("Messages.NOT_ENOUGH_MONEY", NOT_ENOUGH_MONEY);
            yamler.getCfg().addDefault("Messages.RANDOM_ITEM", RANDOM_ITEM);
            yamler.getCfg().addDefault("Messages.RANDOM_ITEM_MONEY", RANDOM_ITEM_MONEY);
            yamler.getCfg().options().copyDefaults(true);
            yamler.save();
            yamler.reload();
        }

    }

    public boolean messagesExists() {
        return new File(JasCollection.getInstance().getDataFolder(), "messages.yml").exists();
    }

    public void load() {
        if(!messagesExists()) {
            create();
        } else {
            File messages = new File(JasCollection.getInstance().getDataFolder(), "messages.yml");
            yamler = new Yamler(messages);
            UNLOCKED = yamler.getCfg().getString("Messages.ITEM_UNLOCKED");
            ITEM_ON = yamler.getCfg().getString("Messages.ITEM_ON");
            ITEMS_OFF = yamler.getCfg().getString("Messages.ITEMS_OFF");
            NOT_ENOUGH_MONEY = yamler.getCfg().getString("Messages.NOT_ENOUGH_MONEY");
            RANDOM_ITEM = yamler.getCfg().getString("Messages.RANDOM_ITEM");
            RANDOM_ITEM_MONEY = yamler.getCfg().getString("Messages.RANDOM_ITEM_MONEY");
        }
    }

    public void reload() {
        yamler.reload();
        load();
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
