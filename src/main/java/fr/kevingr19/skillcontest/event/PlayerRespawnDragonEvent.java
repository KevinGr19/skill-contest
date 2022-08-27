package fr.kevingr19.skillcontest.event;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player respawns the Ender Dragon.
 */

public class PlayerRespawnDragonEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final OfflinePlayer offlinePlayer;

    public PlayerRespawnDragonEvent(OfflinePlayer offlinePlayer){
        this.offlinePlayer = offlinePlayer;
    }

    public OfflinePlayer getOfflinePlayer(){
        return offlinePlayer;
    }
}
