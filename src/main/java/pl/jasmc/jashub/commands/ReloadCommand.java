package pl.jasmc.jashub.commands;

import com.mojang.authlib.GameProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.gui.ScrollerInventory;
import pl.jasmc.jashub.objects.CollectionItem;
import pl.jasmc.jashub.objects.CollectionStorage;
import pl.jasmc.jashub.objects.MetaStorage;
import pl.jasmc.jashub.util.ItemBuilder;
import pl.jasmc.jashub.util.SkinChanger;
import pl.jasmc.jashub.util.UtilItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ReloadCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("collection")) {
            Player player = (Player) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("otworz")) {

                openCollection(player);
                //for (String name : JasCollection.getItemBase().getCfg().getConfigurationSection("").getKeys(false)) {
                //    items.add(JasCollection.getItemBase().getCfg().getItemStack(name+ ".itemstack"));
               // }

            } else if (args.length == 3 && args[0].equalsIgnoreCase("dodaj")) {
                //collection dodaj nazwa cena
                ItemStack itemUnlocked = new ItemBuilder(player.getItemInHand().getType()).setDurability(player.getItemInHand().getData().getData()).addLoreLine(" ")
                        .addLoreLine(" ")
                        .addLoreLine(color("&e&l✦ &d&lPRZEDMIOT &a» &b&l" + args[1].toUpperCase().replace('_', ' ')))
                        .addLoreLine(color("&e&l✦ &6&lCENA &a» &6&l" + args[2]))
                        .addLoreLine(color("&e&l✦ &6&lSTATUS &a» &c&lZABLOKOWANE &4&l✖"))
                        .addLoreLine(color("  "))
                        .addLoreLine(color("        &a&l↓ &6&lOPIS &a&l↓"))
                        .addLoreLine(color("&7DO NAPISANIA W ITEMBASE"))
                        .addLoreLine(color("&7DO NAPISANIA W ITEMBASE"))
                        .addLoreLine(color("&7DO NAPISANIA W ITEMBASE"))
                        .addLoreLine(color("  "))
                        .addLoreLine(color("&7&l----{Kliknij aby doblokowac}----"))
                        .toItemStack();
                addToItemBase(args[1], itemUnlocked, Integer.parseInt(args[2]), getNextID());
                player.sendMessage(color("&a&lJasMC » &ePomyslnie dodano nowy item do ItemBase"));
            } else if(args.length == 1 && args[0].equalsIgnoreCase("monety")) {
                player.sendMessage("Stan konta to: " + MetaStorage.getPlayerMeta(player.getName()).getCoins());
            } else if(args.length == 1 && args[0].equalsIgnoreCase("debug")) {
                CraftPlayer craftPlayer = (CraftPlayer) player;
                //GameProfile gameProfile = new GameProfile(UUID.fromString("c0c36063-ef78-4596-b3db-0c0857aed964"), "JDabrowski");
                SkinChanger.setSkin(((CraftPlayer) player).getProfile(), UUID.fromString("c0c36063-ef78-4596-b3db-0c0857aed964"));

                player.sendMessage("Ustawiono skina na: JDabrowski ");

            }


        }
        return false;
    }

    private void addToItemBase(String name, ItemStack itemStack, int price, int id) {
        JasCollection.getItemBase().getCfg().set(name + ".price", price);
        JasCollection.getItemBase().getCfg().set(name + ".id", id);
        JasCollection.getItemBase().getCfg().set(name + ".itemstack", itemStack);
        JasCollection.getItemBase().save();
        JasCollection.getItemBase().reload();
        CollectionStorage.loadCollection();

    }

    public int getNextID() {
        int i = JasCollection.getItemBase().getCfg().getConfigurationSection("").getKeys(false).size();
        return i+1;
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void openCollection(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for(CollectionItem item : MetaStorage.getPlayerMeta(player.getName()).getAllItems()) {
            if (item.isUnlocked() == false) {
                ItemMeta im = item.getItemStack().getItemMeta();


                List<String> lockedLore = im.getLore();
                player.sendMessage("locked: " + item.getId());
                lockedLore.set(4, "§e§l✦ §6§lSTATUS §a» §c§lZABLOKOWANE §4§l✖");
                lockedLore.set(lockedLore.size()-1, color("&7&l----{Kliknij aby odblokowac}----"));
                im.setLore(lockedLore);
                item.getItemStack().setItemMeta(im);
                items.add(item.getItemStack());

            } else {
                ItemMeta im = item.getItemStack().getItemMeta();
                List<String> unlockedLore = im.getLore();
                unlockedLore.set(4, "§e§l✦ §6§lSTATUS §a» §a§lODBLOKOWANE §2§l✔");
                unlockedLore.set(unlockedLore.size()-1, color("&7&l----{Kliknij aby aktywowac}----"));
                im.setLore(unlockedLore);
                item.getItemStack().setItemMeta(im);
                player.sendMessage("unlocked: " + item.getId());
                items.add(item.getItemStack());
            }

        }
        new ScrollerInventory(items, color(JasCollection.COLLECTION_MENU), player);
    }
}
