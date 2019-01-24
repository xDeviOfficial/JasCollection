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

    }

    public void load() {



        File messages = new File(JasCollection.getInstance().getDataFolder(), "messages.yml");
        if(!messages.exists()) {
            yamler = new Yamler(messages);
            yamler.getCfg().addDefault("Messages.ITEM_UNLOCKED", UNLOCKED);


        }


    }

    public void reload() {

    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
