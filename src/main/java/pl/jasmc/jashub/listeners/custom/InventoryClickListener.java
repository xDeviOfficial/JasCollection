package pl.jasmc.jashub.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.objects.CollectionItem;
import pl.jasmc.jashub.objects.MetaStorage;
import pl.jasmc.jashub.objects.PlayerMeta;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getCurrentItem() == null) return;
        if(event.getSlotType() == InventoryType.SlotType.ARMOR)
        {
            event.setCancelled(true);
            return;
        }


        if(event.getClickedInventory().getName().equalsIgnoreCase(color(JasCollection.COLLECTION_MENU))) {
            InventoryAction a = event.getAction();
            if(a.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || a.equals(InventoryAction.CLONE_STACK)  || a.equals(InventoryAction.HOTBAR_SWAP) || a.equals(InventoryAction.HOTBAR_MOVE_AND_READD) || a.equals(InventoryAction.UNKNOWN)) {
                event.setCancelled(true);
                return;
            }
            if(event.isLeftClick()) {
                if(event.getCurrentItem() != null) {
                    if(event.getCurrentItem().hasItemMeta()) { ;
                        PlayerMeta meta = MetaStorage.getPlayerMeta(event.getWhoClicked().getName());
                        for(CollectionItem item : meta.getAllItems()) {
                            if(event.getCurrentItem().equals(item.getItemStack())) {
                                Bukkit.getServer().getPluginManager().callEvent(new CollectionClickEvent(item, meta, event.getClickedInventory()));
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
            else if(event.isShiftClick()) {
                event.setCancelled(true);
                return;
            } else if(event.isRightClick()) {
                event.setCancelled(true);
                return;
            } else {
                event.setCancelled(true);
                return;
            }


        }

    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}


