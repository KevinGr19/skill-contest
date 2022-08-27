package fr.kevingr19.skillcontest.event;

import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player charges a respawn anchor to its maximum.
 */

public class MaxChargeAnchorEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final RespawnAnchor anchor;

    public MaxChargeAnchorEvent(Player player, RespawnAnchor anchor){
        this.player = player;
        this.anchor = anchor;
    }

    public Player getPlayer() {
        return player;
    }
    public RespawnAnchor getAnchor() {
        return anchor;
    }
}
