package pl.jasmc.jashub;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.jasmc.jashub.commands.CollectionCommand;
import pl.jasmc.jashub.database.DatabaseConfiguration;
import pl.jasmc.jashub.gui.listeners.GUIListener;
import pl.jasmc.jashub.listeners.CollectionClickListener;
import pl.jasmc.jashub.listeners.JoinListener;
import pl.jasmc.jashub.listeners.custom.InventoryClickListener;
import pl.jasmc.jashub.listeners.custom.events.UpdateEvent;
import pl.jasmc.jashub.listeners.custom.events.UpdateType;
import pl.jasmc.jashub.objects.CollectionStorage;
import pl.jasmc.jashub.particles.UnlockedParticle;
import pl.jasmc.jashub.yamler.Messages;
import pl.jasmc.jashub.yamler.Yamler;

import java.io.File;
import java.sql.SQLException;

public final class JasCollection extends JavaPlugin {

    public static boolean DEBUG;

    public static String COLLECTION_MENU;

    private static Messages messages;

    private static Yamler config;
    private static Yamler itemBase;
    private static HikariDataSource hikari;

    public static Yamler getItemBase() {
        return itemBase;
    }

    public static HikariDataSource getHikari() {
        return hikari;
    }

    public static Yamler getYamler() {
        return config;
    }

    public static Messages getMessages() {
        return messages;
    }

    private static JasCollection inst;

    public static JasCollection getInstance() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        this.loadConfig();
        this.loadItemBase();
        this.loadDatabase();
        this.registerCommands();
        this.registerListeners();
        this.loadMessages();
        new runUpdater(this);
    }

    @Override
    public void onDisable() {
        try {
            getHikari().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        File f = new File(getDataFolder(), "config.yml");
        if(!f.exists()) {
            config = new Yamler(f);
            config.getCfg().addDefault("General.DebugMode", true);
            config.getCfg().addDefault("General.Database.DatabaseUse", true);
            config.getCfg().addDefault("General.Database.DataSourceClass", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            config.getCfg().addDefault("General.Database.ServerIP", "127.0.0.1");
            config.getCfg().addDefault("General.Database.ServerPort", "3306");
            config.getCfg().addDefault("General.Database.DatabaseName", "JasCollection");
            config.getCfg().addDefault("General.Database.DatabaseUser", "minecraft");
            config.getCfg().addDefault("General.Database.DatabasePassword", "password");
            config.getCfg().addDefault("Inventory.name", "Kolekcja");
            config.getCfg().options().copyDefaults(true);
            COLLECTION_MENU = "Kolekcja";
            DEBUG = true;
            config.save();
            return;
        }
        config = new Yamler(f);
        DEBUG = config.getCfg().getBoolean("General.DebugMode");

        COLLECTION_MENU = config.getCfg().getString("Inventory.name");
    }

    private void loadMessages() {
        messages = new Messages();
        messages.load();
    }



    private void loadItemBase() {
        File itembase = new File(getDataFolder(), "itemBase.yml");
        if(!itembase.exists()) {
            itemBase = new Yamler(itembase);
            itemBase.save();
            return;
        }
        itemBase = new Yamler(itembase);
        CollectionStorage.loadCollection();
    }
    private void registerCommands() {
        getCommand("kolekcja").setExecutor(new CollectionCommand());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new GUIListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new CollectionClickListener(), this);
        pm.registerEvents(new UnlockedParticle(), this);
    }

    public void loadDatabase() {
        if(config != null) {
            if(config.getCfg().getBoolean("General.Database.DatabaseUse")) {
                hikari = new HikariDataSource();
                hikari.setDataSourceClassName(config.getCfg().getString("General.Database.DataSourceClass"));
                hikari.addDataSourceProperty("serverName", config.getCfg().getString("General.Database.ServerIP"));
                hikari.addDataSourceProperty("port", config.getCfg().getString("General.Database.ServerPort"));
                hikari.addDataSourceProperty("databaseName", config.getCfg().getString("General.Database.DatabaseName"));
                hikari.addDataSourceProperty("user", config.getCfg().getString("General.Database.DatabaseUser"));
                hikari.addDataSourceProperty("password", config.getCfg().getString("General.Database.DatabasePassword"));
                try {
                    DatabaseConfiguration.checkTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                if(DEBUG) {
                    System.out.println("[DEBUG] Support dla MySQL wylaczony.");
                }
            }
        } else {
            if(DEBUG) {
                System.out.println("[DEBUG] Yamler = NULL");
            }
        }
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public class runUpdater
    {
        public runUpdater(JavaPlugin plugin) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Fastest(), 0L, 1L);
        }

        protected class Fastest implements Runnable
        {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new UpdateEvent(UpdateType.FASTEST));
            }
        }

    }




}




