package pl.jasmc.jashub.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.jasmc.jashub.database.DatabaseConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerMeta {

    protected UUID uuid;
    protected String name;
    protected int coins;
    protected int idInBase;
    protected List<CollectionItem> allItems = new ArrayList<>();

    public PlayerMeta(String name) {
        super();
        this.name = name;
    }

    public boolean isUnlocked(int id) {
        for(CollectionItem item : allItems) {
            if(item.getId() == id) {
                if(item.isUnlocked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public CollectionItem getItemByID(int id) {
        for(CollectionItem item : allItems) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<CollectionItem> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<CollectionItem> allItems) {
        this.allItems = allItems;
    }


    public Player getPlayer() {
        return Bukkit.getPlayer(name);
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
        DatabaseConfiguration.setCoins(this, coins);
    }

    public void addCoins(int coinsToAdd) {
        this.coins = this.coins+coinsToAdd;
        DatabaseConfiguration.addCoins(this, coinsToAdd);
    }

    public void removeCoins(int coinsToRemove) {
        this.coins = this.coins-coinsToRemove;
        DatabaseConfiguration.removeCoins(this, coinsToRemove);
    }


    public int getIdInBase() {
        return idInBase;
    }

    public void setIdInBase(int idInBase) {
        this.idInBase = idInBase;
    }
}
