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
import pl.jasmc.jashub.objects.MetaStorage;
import pl.jasmc.jashub.objects.PlayerMeta;
import pl.jasmc.jashub.util.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
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
        Inventory page = getBlankPage(name, MetaStorage.getPlayerMeta(p.getName()));
        //According to the items in the arraylist, add items to the ScrollerInventory
        for(int i = 0;i < items.size(); i++){
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            System.out.println("First empty: " + page.firstEmpty());
            if(page.firstEmpty() == 46 || page.firstEmpty() == -1 ){
                pages.add(page);
                page = getBlankPage(name, MetaStorage.getPlayerMeta(p.getName()));
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


    public static final String coinsName = ChatColor.GOLD + " ";
    public static final String emptyString = " ";
    public static final String disableEffectsName = ChatColor.RED + "Zdejmij przedmioty";
    public static final String nextPageName = ChatColor.AQUA + "Nastepna strona";
    public static final String previousPageName = ChatColor.AQUA + "Poprzednia strona";
    public static final String closeItem = ChatColor.RED + "Zamknij";
    //This creates a blank page with the next and prev buttons
    private Inventory getBlankPage(String name, PlayerMeta owner){
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

        ItemStack goldenPowder = new ItemStack(Material.BLAZE_POWDER, 1);
        meta = goldenPowder.getItemMeta();
        meta.setDisplayName(coinsName);
        meta.setLore(Arrays.asList("    ", ChatColor.GOLD + "    " + ChatColor.BOLD + " Zlocisty proch: " + owner.getCoins() + "   ", "   ", "  "));
        goldenPowder.setItemMeta(meta);






        ItemStack greenGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 5).setName(emptyString).toItemStack();
        ItemStack yellowGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 4).setName(emptyString).toItemStack();


        //GORA
        page.setItem(0, yellowGlass);
        page.setItem(1, greenGlass);
        page.setItem(2, greenGlass);
        page.setItem(3, yellowGlass);
        page.setItem(4, greenGlass);
        page.setItem(5, yellowGlass);
        page.setItem(6, greenGlass);
        page.setItem(7, greenGlass);
        page.setItem(8, yellowGlass);



        //LWEA STRONA
        //1 rzad od lewej
        page.setItem(9, greenGlass);
        page.setItem(18, yellowGlass);
        page.setItem(27, greenGlass);
        page.setItem(36, goldenPowder);

        //2rzad od lewej - szare puste
        page.setItem(10, emptySlot);
        page.setItem(19 ,emptySlot);
        page.setItem(28 ,emptySlot);
        page.setItem(37 ,emptySlot);


        //PRAWA STRONA
        //1rzad
        page.setItem(17, greenGlass);
        page.setItem(26, yellowGlass);
        page.setItem(35, greenGlass);
        page.setItem(45, yellowGlass);
        //2rzad - szare puste
        page.setItem(16 ,emptySlot);
        page.setItem(25 ,emptySlot);
        page.setItem(34 ,emptySlot);
        page.setItem(43 ,emptySlot);

        //DOL

        page.setItem(46, greenGlass);
        page.setItem(47 ,prevpage);
        page.setItem(48, greenGlass);
        page.setItem(49, close);
        page.setItem(50, greenGlass);
        page.setItem(51, nextpage);
        page.setItem(52, greenGlass);
        page.setItem(53, yellowGlass);
        page.setItem(44, disableEffects);


        return page;
    }
}