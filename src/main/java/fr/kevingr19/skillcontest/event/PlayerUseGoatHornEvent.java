package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Event called when a player uses a goat horn.
 */

public class PlayerUseGoatHornEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final ItemStack horn;

    public PlayerUseGoatHornEvent(Player player, ItemStack horn){
        this.player = player;
        this.horn = horn;
    }

    public Player getPlayer() {
        return player;
    }
    public ItemStack getHorn() {
        return horn;
    }
}
