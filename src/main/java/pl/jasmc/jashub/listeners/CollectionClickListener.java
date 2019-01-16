package pl.jasmc.jashub.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.commands.ReloadCommand;
import pl.jasmc.jashub.database.DatabaseConfiguration;
import pl.jasmc.jashub.gui.ScrollerInventory;
import pl.jasmc.jashub.listeners.custom.CollectionClickEvent;

public class CollectionClickListener implements Listener {

    @EventHandler
    public void onCollectionItemClick(CollectionClickEvent event) {
        if(event.getItem().isUnlocked()) {
            Material mat = event.getItem().getItemStack().getType();
            if(mat.equals(Material.CHAINMAIL_HELMET) || mat.equals(Material.DIAMOND_HELMET) || mat.equals(Material.GOLD_HELMET) || mat.equals(Material.IRON_HELMET) || mat.equals(Material.LEATHER_HELMET)) {
                event.getMeta().getPlayer().getInventory().setHelmet(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color("&a&lJasMC » &bZalozyles &a" + event.getItem().getName()));
            } else if(mat.equals(Material.CHAINMAIL_CHESTPLATE) || mat.equals(Material.DIAMOND_CHESTPLATE) || mat.equals(Material.GOLD_CHESTPLATE) || mat.equals(Material.IRON_CHESTPLATE) || mat.equals(Material.LEATHER_CHESTPLATE)) {
                event.getMeta().getPlayer().getInventory().setChestplate(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color("&a&lJasMC » &bZalozyles &a" + event.getItem().getName()));
            } else if(mat.equals(Material.CHAINMAIL_LEGGINGS) || mat.equals(Material.DIAMOND_LEGGINGS) || mat.equals(Material.GOLD_LEGGINGS) || mat.equals(Material.IRON_LEGGINGS) || mat.equals(Material.LEATHER_LEGGINGS)) {
                event.getMeta().getPlayer().getInventory().setLeggings(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color("&a&lJasMC » &bZalozyles &a" + event.getItem().getName()));
            } else if(mat.equals(Material.CHAINMAIL_BOOTS) || mat.equals(Material.DIAMOND_BOOTS) || mat.equals(Material.GOLD_BOOTS) || mat.equals(Material.IRON_BOOTS) || mat.equals(Material.LEATHER_BOOTS)) {
                event.getMeta().getPlayer().getInventory().setBoots(event.getItem().getItemStack());
                event.getMeta().getPlayer().sendMessage(color("&a&lJasMC » &bZalozyles &a" + event.getItem().getName()));
            }
        } else {
            if(event.getMeta().getCoins() >= event.getItem().getPrice()) {
                DatabaseConfiguration.removeCoins(event.getMeta(), event.getItem().getPrice());
                DatabaseConfiguration.unlockItem(event.getItem().getId(), event.getMeta());
                event.getItem().setUnlocked(true);
                //event.getMeta().getPlayer().updateInventory();
                //event.getMeta().getPlayer().closeInventory();
                //ReloadCommand.openCollection(event.getMeta().getPlayer());
                event.getMeta().getPlayer().sendMessage(color("&a&lJasMC » &bPomyslnie odblokowano &a" + event.getItem().getName()));
                //DatabaseConfiguration.unlockItem();
            } else {
                event.getMeta().getPlayer().sendMessage(color("&a&lJasMC » &cNie posiadasz wystarczajaco &6&lZLOCISTEGO PYLU"));
            }
        }
        //event.getMeta().getPlayer().sendMessage("Kliknieto item o ID: " + event.getItem().getId());
        //event.getMeta().getPlayer().sendMessage("Jego cena wynosi: " + event.getItem().getPrice());
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }


}
