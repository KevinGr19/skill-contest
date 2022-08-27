package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player is targeted by an enderman.
 */

public class EndermanTargetPlayerEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final long count;

    public EndermanTargetPlayerEvent(Player player, long count){
        this.player = player;
        this.count = count;
    }

    public Player getPlayer() {
        return player;
    }
    public long getCount() {
        return count;
    }
}
