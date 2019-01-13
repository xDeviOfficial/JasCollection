package pl.jasmc.jashub.util;


import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UtilItem extends ItemStack {

    public UtilItem(Material m) {
        super(m);
    }

    public UtilItem(Material m, int a) {
        super(m, a);
    }

    public UtilItem(Material m, int a, byte d) {
        super(m, a, d);
    }

    public UtilItem(Material m, String name) {
        super(m);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        this.setItemMeta(im);
    }

    public UtilItem(Material m, String name, List<String> lore) {
        super(m);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        this.setItemMeta(im);
    }

    public UtilItem(Material m, int i, String name) {
        super(m, i);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        this.setItemMeta(im);
    }

    public UtilItem(Material m, int i, byte d, String name) {
        super(m, i, d);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        this.setItemMeta(im);
    }

    public UtilItem(Material m, int i, String name, List<String> lore) {
        super(m, i);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        this.setItemMeta(im);
    }

    public UtilItem(Material m, int i, byte d, String name, List<String> lore) {
        super(m, i, d);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        this.setItemMeta(im);
    }
}