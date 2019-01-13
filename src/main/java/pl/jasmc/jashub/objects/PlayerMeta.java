package pl.jasmc.jashub.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
    }

    public int getIdInBase() {
        return idInBase;
    }

    public void setIdInBase(int idInBase) {
        this.idInBase = idInBase;
    }
}
