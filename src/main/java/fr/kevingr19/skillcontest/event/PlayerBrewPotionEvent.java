package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.meta.PotionMeta;

/**
 * Event called when a player takes a newly brewed potion from a brewing stand,
 * or breaks a brewing stand containing newly brewed potions.
 */

public class PlayerBrewPotionEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final PotionMeta potionMeta;

    public PlayerBrewPotionEvent(Player player, PotionMeta meta){
        this.player = player;
        this.potionMeta = meta;
    }

    public Player getPlayer() {
        return player;
    }
    public PotionMeta getPotionMeta(){
        return potionMeta;
    }
}
