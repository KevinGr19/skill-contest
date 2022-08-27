package fr.kevingr19.skillcontest.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Event called when a player closes a double chest.
 */

public class DoubleChestClosedEvent extends Event {

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
    private final Location loc1;
    private final Location loc2;

    public DoubleChestClosedEvent(Player player, Location loc1, Location loc2, Inventory inventory){
        this.player = player;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }
    public Location getLoc1() {
        return loc1;
    }
    public Location getLoc2() {
        return loc2;
    }
    public Inventory getInventory() {
        return inventory;
    }
}
