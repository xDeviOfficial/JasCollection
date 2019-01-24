package pl.jasmc.jashub.listeners.custom.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event
{
    private UpdateType type;
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    public UpdateEvent(final UpdateType type) {
        this.type = type;
    }

    public UpdateType getType() {
        return this.type;
    }

    public HandlerList getHandlers() {
        return UpdateEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return UpdateEvent.handlers;
    }
}
