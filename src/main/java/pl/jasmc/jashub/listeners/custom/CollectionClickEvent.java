package pl.jasmc.jashub.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.jasmc.jashub.objects.CollectionItem;
import pl.jasmc.jashub.objects.PlayerMeta;

public class CollectionClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private CollectionItem item;
    private PlayerMeta meta;
    private Inventory inv;
    private ItemStack currentItem;

    public Inventory getInv() {
        return inv;
    }

    public CollectionClickEvent(CollectionItem collectionItem, PlayerMeta meta, Inventory openedInv) {
        this.item = collectionItem;
        this.meta = meta;
        this.inv = openedInv;
    }

    public CollectionItem getItem() {
        return item;
    }

    public PlayerMeta getMeta() {
        return meta;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return false;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
