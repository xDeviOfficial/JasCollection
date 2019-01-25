package pl.jasmc.jashub.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.database.DatabaseConfiguration;
import pl.jasmc.jashub.listeners.custom.CollectionClickEvent;
import pl.jasmc.jashub.particles.ParticleHandler;
import pl.jasmc.jashub.particles.ParticleType;
import pl.jasmc.jashub.yamler.Messages;

public class CollectionClickListener implements Listener {

    @EventHandler
    public void onCollectionItemClick(CollectionClickEvent event) {
        if(event.getItem().isUnlocked()) {
            Material mat = event.getItem().getItemStack().getType();
            if(mat.equals(Material.CHAINMAIL_HELMET) || mat.equals(Material.DIAMOND_HELMET) || mat.equals(Material.GOLD_HELMET) || mat.equals(Material.IRON_HELMET) || mat.equals(Material.LEATHER_HELMET) || mat.equals(Material.SKULL_ITEM)) {
                event.getMeta().getPlayer().getInventory().setHelmet(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color(Messages.ITEM_ON.replace("{item}", event.getItem().getName().replace("_", " "))));
            } else if(mat.equals(Material.CHAINMAIL_CHESTPLATE) || mat.equals(Material.DIAMOND_CHESTPLATE) || mat.equals(Material.GOLD_CHESTPLATE) || mat.equals(Material.IRON_CHESTPLATE) || mat.equals(Material.LEATHER_CHESTPLATE)) {
                event.getMeta().getPlayer().getInventory().setChestplate(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color(Messages.ITEM_ON.replace("{item}", event.getItem().getName().replace("_", " "))));
            } else if(mat.equals(Material.CHAINMAIL_LEGGINGS) || mat.equals(Material.DIAMOND_LEGGINGS) || mat.equals(Material.GOLD_LEGGINGS) || mat.equals(Material.IRON_LEGGINGS) || mat.equals(Material.LEATHER_LEGGINGS)) {
                event.getMeta().getPlayer().getInventory().setLeggings(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color(Messages.ITEM_ON.replace("{item}", event.getItem().getName().replace("_", " "))));
            } else if(mat.equals(Material.CHAINMAIL_BOOTS) || mat.equals(Material.DIAMOND_BOOTS) || mat.equals(Material.GOLD_BOOTS) || mat.equals(Material.IRON_BOOTS) || mat.equals(Material.LEATHER_BOOTS)) {
                event.getMeta().getPlayer().getInventory().setBoots(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color(Messages.ITEM_ON.replace("{item}", event.getItem().getName().replace("_", " "))));
            }
        } else {
            if(event.getMeta().getCoins() >= event.getItem().getPrice()) {
                DatabaseConfiguration.removeCoins(event.getMeta(), event.getItem().getPrice());
                DatabaseConfiguration.unlockItem(event.getItem().getId(), event.getMeta());
                event.getItem().setUnlocked(true);

                event.getMeta().getPlayer().sendMessage(color(Messages.UNLOCKED.replace("{item}", event.getItem().getName().replace("_", " "))));
                event.getMeta().getPlayer().closeInventory();
                if (ParticleHandler.getParticle(event.getMeta().getPlayer()) == ParticleType.NONE) {
                    ParticleHandler.activateParticle(event.getMeta().getPlayer(), ParticleType.UNLOCKED);
                }

                Bukkit.getScheduler().runTaskLaterAsynchronously(JasCollection.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ParticleHandler.deactivateParticle(event.getMeta().getPlayer());
                    }
                }, 40);

            } else {
                event.getMeta().getPlayer().sendMessage(color(Messages.NOT_ENOUGH_MONEY.replace("{needed}", String.valueOf(event.getItem().getPrice())).replace("{coins}", String.valueOf(event.getMeta().getCoins()))));
            }
        }
        //event.getMeta().getPlayer().sendMessage("Kliknieto item o ID: " + event.getItem().getId());
        //event.getMeta().getPlayer().sendMessage("Jego cena wynosi: " + event.getItem().getPrice());
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }


}
