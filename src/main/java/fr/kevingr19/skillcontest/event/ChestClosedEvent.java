package fr.kevingr19.skillcontest.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Event called when a player closes a chest.
 */

public class ChestClosedEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Inventory inventory;
    private final Location location;

    public ChestClosedEvent(Player player, Location loc, Inventory inventory){
        this.player = player;
        this.location = loc;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }
    public Location getLocation() {
        return location;
    }
    public Inventory getInventory() {
        return inventory;
    }
}
