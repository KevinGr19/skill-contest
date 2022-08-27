package fr.kevingr19.skillcontest.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Event called when a piglin receives a gold ingot from an entity.
 */

public class PiglinGetGoldIngotEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final UUID uuid;
    private final ItemStack itemStack;

    public PiglinGetGoldIngotEvent(UUID uuid, ItemStack itemStack){
        this.uuid = uuid;
        this.itemStack = itemStack;
    }

    public UUID getThrower() {
        return uuid;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }
}
