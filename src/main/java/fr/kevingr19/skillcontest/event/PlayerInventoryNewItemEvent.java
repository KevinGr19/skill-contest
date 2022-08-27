package fr.kevingr19.skillcontest.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Event called when a player gets a new item in their inventory.
 * This should be called only to check for new materials, or start inventory checks.
 */

public class PlayerInventoryNewItemEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final ItemStack item;

    public PlayerInventoryNewItemEvent(Player player, @Nonnull ItemStack item){
        this.player = player;
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }
    public ItemStack getItem(){
        return item;
    }
    public Material getMaterial(){
        return item.getType();
    }

}
