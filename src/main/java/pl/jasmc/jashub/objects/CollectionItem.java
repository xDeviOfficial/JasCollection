package pl.jasmc.jashub.objects;

import org.bukkit.inventory.ItemStack;

public class CollectionItem {

    protected ItemStack itemStack;
    protected boolean unlocked;
    protected int price;
    protected int id;
    protected String name;

    public CollectionItem(ItemStack item, int id, int price, String name) {
        this.itemStack = item;
        this.id = id;
        this.price = price;
        this.unlocked = false;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
