package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player is next to a newly spawned wither.
 */

public class PlayerCloseToSpawnWitherEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Wither wither;

    public PlayerCloseToSpawnWitherEvent(Player player, Wither wither){
        this.player = player;
        this.wither = wither;
    }

    public Player getPlayer() {
        return player;
    }
    public Wither getWither() {
        return wither;
    }
}
