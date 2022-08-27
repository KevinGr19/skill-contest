package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player blocks an incoming source of damage.
 */

public class PlayerBlockDamageEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Entity damager;

    public PlayerBlockDamageEvent(Player player, Entity damager){
        this.player = player;
        this.damager = damager;
    }

    public Player getPlayer() {
        return player;
    }
    public Entity getDamager() {
        return damager;
    }
}
