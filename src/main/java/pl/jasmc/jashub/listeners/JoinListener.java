package pl.jasmc.jashub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.jasmc.jashub.database.DatabaseConfiguration;
import pl.jasmc.jashub.objects.MetaStorage;
import pl.jasmc.jashub.objects.PlayerMeta;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        MetaStorage.storeMeta(event.getPlayer().getName());

        final PlayerMeta meta = MetaStorage.getPlayerMeta(event.getPlayer().getName());
        meta.setUuid(event.getPlayer().getUniqueId());
        DatabaseConfiguration.loadMetaAndCreateIfNotExist(meta);

        //DatabaseConfiguration.loadUnlockedItems(meta);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        MetaStorage.deleteMeta(event.getPlayer().getName());
    }

}
