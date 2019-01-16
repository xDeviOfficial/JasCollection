package pl.jasmc.jashub.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import pl.jasmc.jashub.util.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ScrollerInventory{

    public ArrayList<Inventory> pages = new ArrayList<Inventory>();
    public UUID id;
    public int currpage = 0;
    public static HashMap<UUID, ScrollerInventory> users = new HashMap<UUID, ScrollerInventory>();
    //Running this will open a paged inventory for the specified player, with the items in the arraylist specified.
    public ScrollerInventory(ArrayList<ItemStack> items, String name, Player p){
        this.id = UUID.randomUUID();
//create new blank page
        Inventory page = getBlankPage(name);
        //According to the items in the arraylist, add items to the ScrollerInventory
        for(int i = 0;i < items.size(); i++){
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            System.out.println("First empty: " + page.firstEmpty());
            if(page.firstEmpty() == 46 || page.firstEmpty() == -1 ){
                pages.add(page);
                page = getBlankPage(name);
                page.addItem(items.get(i));
            }else{
//Add the item to the current page as per normal
                page.addItem(items.get(i));
            }
        }
        pages.add(page);
//open page 0 for the specified player
        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }



    public static final String emptyString = " ";
    public static final String disableEffectsName = ChatColor.RED + "Wulacz efekty";
    public static final String nextPageName = ChatColor.AQUA + "Nastepna strona";
    public static final String previousPageName = ChatColor.AQUA + "Poprzednia strona";
    public static final String closeItem = ChatColor.RED + "Zamknij";
    //This creates a blank page with the next and prev buttons
    private Inventory getBlankPage(String name){
        Inventory page = Bukkit.createInventory(null, 54, name);


        ItemStack nextpage =  new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta meta = nextpage.getItemMeta();
        meta.setDisplayName(nextPageName);
        nextpage.setItemMeta(meta);

        ItemStack prevpage = new ItemStack(Material.SPECTRAL_ARROW, 1);
        meta = prevpage.getItemMeta();
        meta.setDisplayName(previousPageName);
        prevpage.setItemMeta(meta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        meta = close.getItemMeta();
        meta.setDisplayName(closeItem);
        close.setItemMeta(meta);

        ItemStack emptySlot = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 8);
        meta = emptySlot.getItemMeta();
        meta.setDisplayName(emptyString);
        emptySlot.setItemMeta(meta);

        ItemStack disableEffects = new ItemStack(Material.BUCKET, 1);
        meta = disableEffects.getItemMeta();
        meta.setDisplayName(disableEffectsName);
        disableEffects.setItemMeta(meta);






        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 10).setName(emptyString).toItemStack();
        for(int glassPos = 0; glassPos < 9; glassPos++) {
            page.setItem(glassPos, glass);
        }

        //LWEA STRONA
        //1 rzad od lewej
        page.setItem(9, glass);
        page.setItem(18, glass);
        page.setItem(27, glass);
        page.setItem(36, glass);
        //2rzad od lewej
        page.setItem(10, emptySlot);
        page.setItem(19 ,emptySlot);
        page.setItem(28 ,emptySlot);
        page.setItem(37 ,emptySlot);


        //PRAWA STRONA
        //1rzad
        page.setItem(17, glass);
        page.setItem(26, glass);
        page.setItem(35, glass);
        page.setItem(45, glass);
        //2rzad
        page.setItem(16 ,emptySlot);
        page.setItem(25 ,emptySlot);
        page.setItem(34 ,emptySlot);
        page.setItem(43 ,emptySlot);

        //DOL

        page.setItem(46, prevpage);
        page.setItem(47 ,glass);
        page.setItem(48, glass);
        page.setItem(49, close);
        page.setItem(50, glass);
        page.setItem(51, glass);
        page.setItem(52, nextpage);
        page.setItem(53, glass);
        page.setItem(44, disableEffects);


        return page;
    }
}