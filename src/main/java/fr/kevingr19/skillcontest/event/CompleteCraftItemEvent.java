package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Event called when a player crafts an item.
 * Provides more features than {@link org.bukkit.event.inventory.CraftItemEvent}, such as accurate quantity.
 */

public class CompleteCraftItemEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final ItemStack result;
    private final int amount;

    public CompleteCraftItemEvent(Player player, ItemStack result, int amount){
        this.player = player;
        this.result = result;
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }
    public ItemStack getResult() {
        return result;
    }
    public int getAmount() {
        return amount;
    }
}
