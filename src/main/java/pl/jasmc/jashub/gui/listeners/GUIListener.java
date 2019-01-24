package pl.jasmc.jashub.gui.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.gui.ScrollerInventory;
import pl.jasmc.jashub.yamler.Messages;

public class GUIListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        //Get the current scroller inventory the player is looking at, if the player is looking at one.
        if(!ScrollerInventory.users.containsKey(p.getUniqueId())) return;
        ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());

        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        //If the pressed item was a nextpage button
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.nextPageName)){
            event.setCancelled(true);
            //If there is no next page, don't do anything
            if(inv.currpage >= inv.pages.size()-1){
                if(JasCollection.DEBUG) {
                    System.out.println("There is no Next page");
                }
                return;
            }else{
                if(JasCollection.DEBUG) {
                    System.out.println("Loading next page");
                }

                //Next page exists, flip the page
                inv.currpage += 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
            //if the pressed item was a previous page button
        }else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.previousPageName)){
            event.setCancelled(true);
            //If the page number is more than 0 (So a previous page exists)
            if(inv.currpage > 0){
                //Flip to previous page
                inv.currpage -= 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.emptyString)) {
            event.setCancelled(true);
        }
        else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.closeItem)) {
            event.setCancelled(true);
            p.closeInventory();
        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.disableEffectsName)) {
            event.getWhoClicked().getInventory().setHelmet(null);
            event.getWhoClicked().getInventory().setChestplate(null);
            event.getWhoClicked().getInventory().setLeggings(null);
            event.getWhoClicked().getInventory().setBoots(null);

            event.getWhoClicked().sendMessage(JasCollection.color(Messages.ITEMS_OFF));
            event.setCancelled(true);
        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.coinsName)) {
            event.setCancelled(true);
        }
    }
}
