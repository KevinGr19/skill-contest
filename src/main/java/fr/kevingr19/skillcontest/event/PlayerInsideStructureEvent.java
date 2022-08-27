package fr.kevingr19.skillcontest.event;

import net.minecraft.world.level.levelgen.structure.Structure;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called every {@link fr.kevingr19.skillcontest.StructureManager} tick when a player is
 * inside a {@link Structure}.
 */

public class PlayerInsideStructureEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Structure structure;

    public PlayerInsideStructureEvent(Player player, Structure structure){
        this.player = player;
        this.structure = structure;
    }

    public Player getPlayer() {
        return player;
    }
    public Structure getStructure(){
        return structure;
    }

}
