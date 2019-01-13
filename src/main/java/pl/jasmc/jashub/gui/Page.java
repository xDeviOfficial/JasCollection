package pl.jasmc.jashub.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Page {

    public List<ItemStack> itemsOnPage;

    public Page(List<ItemStack> items) {
        this.itemsOnPage = items;
    }
}
