package pl.jasmc.jashub.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.jasmc.jashub.CollectionAPI;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.gui.ScrollerInventory;
import pl.jasmc.jashub.objects.CollectionItem;
import pl.jasmc.jashub.objects.CollectionStorage;
import pl.jasmc.jashub.objects.MetaStorage;
import pl.jasmc.jashub.objects.PlayerMeta;
import pl.jasmc.jashub.particles.ParticleHandler;
import pl.jasmc.jashub.particles.ParticleType;
import pl.jasmc.jashub.util.ItemBuilder;

import java.util.*;

public class ReloadCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("kolekcja")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("group.developer") || player.hasPermission("group.admin") || player.hasPermission("group.minidev") || player.hasPermission("group.wlasciciel")) {
                    if (args.length == 0) {
                        player.sendMessage(color("&c&lWykryto uprawnienia Administratora"));
                        player.sendMessage(color("&d Kolekcja &eJasMC"));
                        player.sendMessage(color("&eDostepne komendy: "));
                        player.sendMessage(color("&d/&ekolekcja otworz &7- Otwiera GUI z kolekcja"));
                        player.sendMessage(color("&d/&ekolekcja dodaj <Nazwa> <Cena> &7- Tworzy nowy item i dodaje do itemBase.yml i kolekcji"));
                        player.sendMessage(color("&d/&ekolekcja usun <ID>  &7- Usuwa item z kolekcji"));
                        player.sendMessage(color("&d/&ekolekcja reload&7- &c&lPrzeladowanie configu"));
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("otworz")) {
                        openCollection(player);
                    } else if(args.length == 3 & args[0].equalsIgnoreCase("dodaj")) {
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

                    } else if(args.length == 2 && args[0].equalsIgnoreCase("usun")) {
                        String name = args[1];
                        removeItemFromCollection(name);
                        player.sendMessage(color("&a&lJasMC » &ePomyslnie usunito item o nazwie: " + name + " z ItemBase"));
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                        JasCollection.getItemBase().reload();
                        JasCollection.getYamler().reload();
                        JasCollection.getInstance().loadConfig();
                        player.sendMessage(color("&a&lJasMC » &ePomyslnie przeladowano Config"));
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("monety")) {
                        player.sendMessage(color("&a&lJasMC » &eZlocisty proch: " + MetaStorage.getPlayerMeta(player.getName()).getCoins()));
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("losuj")) {
                        CollectionAPI.getRandomCollectionItem(MetaStorage.getPlayerMeta(player.getName()));
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("particle")) {
                        if (ParticleHandler.getParticle(player) == ParticleType.NONE) {
                            ParticleHandler.activateParticle(player, ParticleType.UNLOCKED);
                            player.sendMessage("Aktywowales particle");
                        } else {
                            ParticleHandler.deactivateParticle(player);
                            player.sendMessage("Dezaktywowales particle");
                        }


                    }
                } else {
                    if(args.length == 0) {
                        openCollection(player);
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("monety")) {
                        player.sendMessage(color("&a&lJasMC » &eZlocisty proch: " + MetaStorage.getPlayerMeta(player.getName()).getCoins()));
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("losuj")) {
                        CollectionAPI.getRandomCollectionItem(MetaStorage.getPlayerMeta(player.getName()));
                    }

                }
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
        CollectionStorage.reloadCollection();
    }

    private void removeItemFromCollection(String name) {
        JasCollection.getItemBase().getCfg().set(name , null);
        JasCollection.getItemBase().save();
        JasCollection.getItemBase().reload();
        CollectionStorage.reloadCollection();
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
        PlayerMeta meta = MetaStorage.getPlayerMeta(player.getName());
        Collections.sort(meta.getAllItems(), Comparator.comparingInt(CollectionItem::getId));

        for(CollectionItem item : MetaStorage.getPlayerMeta(player.getName()).getAllItems()) {
            if (item.isUnlocked() == false) {
                if(item.getItemStack() != null) {
                    if(item.getItemStack().hasItemMeta()) {
                        ItemMeta im = item.getItemStack().getItemMeta();
                        List<String> lockedLore = im.getLore();
                        if(JasCollection.DEBUG) {
                            player.sendMessage("locked: " + item.getId());
                        }

                        lockedLore.set(4, "§e§l✦ §6§lSTATUS §a» §c§lZABLOKOWANE §4§l✖");
                        lockedLore.set(lockedLore.size()-1, color("&7&l----{Kliknij aby odblokowac}----"));
                        im.setLore(lockedLore);
                        im.setDisplayName(ChatColor.RED +item.getName().replace("_", " "));
                        item.getItemStack().setItemMeta(im);

                        items.add(item.getItemStack());
                    }
                } else {
                    if(JasCollection.DEBUG) {
                        System.out.println("NULL DLA ID " + item.getId());
                    }
                }


            } else {
                if(item.getItemStack() != null) {
                    if(item.getItemStack().hasItemMeta()) {
                        ItemMeta im = item.getItemStack().getItemMeta();
                        List<String> unlockedLore = im.getLore();
                        unlockedLore.set(4, "§e§l✦ §6§lSTATUS §a» §a§lODBLOKOWANE §2§l✔");
                        unlockedLore.set(unlockedLore.size()-1, color("&7&l----{Kliknij aby aktywowac}----"));
                        im.setDisplayName(ChatColor.GREEN + item.getName().replace("_", " "));
                        im.setLore(unlockedLore);
                        item.getItemStack().setItemMeta(im);
                        if(JasCollection.DEBUG) {
                            player.sendMessage("unlocked: " + item.getId());
                        }
                        items.add(item.getItemStack());
                    }
                } else {
                    if(JasCollection.DEBUG) {
                        System.out.println("NULL DLA ID " + item.getId());
                    }
                }

            }

        }
        if(JasCollection.DEBUG) {
            System.out.println("List length: " + items.size());
        }

        new ScrollerInventory(items, color(JasCollection.COLLECTION_MENU), player);
    }
}
