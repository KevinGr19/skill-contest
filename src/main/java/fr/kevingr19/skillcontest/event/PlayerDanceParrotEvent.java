package fr.kevingr19.skillcontest.event;

import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
/**
 * Event called when a player makes a parrot dance (one that is not on a player's shoulder).
 */

public class PlayerDanceParrotEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Jukebox jukebox;
    private final int parrotCount;

    public PlayerDanceParrotEvent(Player player, Jukebox jukebox, int parrotCount){
        this.player = player;
        this.jukebox = jukebox;
        this.parrotCount = parrotCount;
    }

    public Player getPlayer() {
        return player;
    }
    public Jukebox getJukebox() {
        return jukebox;
    }
    public int getParrotCount() {
        return parrotCount;
    }
}
