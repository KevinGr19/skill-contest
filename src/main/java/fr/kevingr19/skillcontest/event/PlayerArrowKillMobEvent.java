package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player kills a non-player mob with an arrow.
 */

public class PlayerArrowKillMobEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final LivingEntity entity;

    public PlayerArrowKillMobEvent(Player player, LivingEntity entity){
        this.player = player;
        this.entity = entity;
    }

    public Player getPlayer() {
        return player;
    }
    public LivingEntity getEntity() {
        return entity;
    }
}
