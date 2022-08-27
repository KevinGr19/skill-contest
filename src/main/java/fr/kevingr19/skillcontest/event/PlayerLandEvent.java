package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player lands on the ground.
 */

public class PlayerLandEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final float fallDistance;

    public PlayerLandEvent(Player player, float fallDistance){
        this.player = player;
        this.fallDistance = fallDistance;
    }

    public Player getPlayer() {
        return player;
    }
    public float getFallDistance() {
        return fallDistance;
    }
}
