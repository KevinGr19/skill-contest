package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player kills a wither skeleton with a potion.
 */

public class WitherSkeletonPotionKillEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;

    public WitherSkeletonPotionKillEvent(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
