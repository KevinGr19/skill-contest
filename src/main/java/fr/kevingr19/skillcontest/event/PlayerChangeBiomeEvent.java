package fr.kevingr19.skillcontest.event;

import org.apache.commons.lang.WordUtils;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player changes of biome.
 */

public class PlayerChangeBiomeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Biome biome;
    private final String name;

    public PlayerChangeBiomeEvent(Player player, Biome biome){
        this.player = player;
        this.biome = biome;
        this.name = WordUtils.capitalize(biome.name().toLowerCase().replace('_', ' '));
    }

    public Player getPlayer() {
        return player;
    }
    public Biome getBiome() {
        return biome;
    }
    public String getBiomeName() {
        return name;
    }
}
