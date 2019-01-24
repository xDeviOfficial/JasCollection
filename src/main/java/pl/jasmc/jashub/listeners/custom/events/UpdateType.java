package pl.jasmc.jashub.listeners.custom.events;

public enum UpdateType
{
    SLOWEST("SLOWEST", 0),
    SLOW("SLOW", 1),
    NORMAL("NORMAL", 2),
    FAST("FAST", 3),
    FASTEST("FASTEST", 4);

    UpdateType(String name, int id) {

    }
}