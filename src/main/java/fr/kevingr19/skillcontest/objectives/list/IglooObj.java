package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class IglooObj extends Objective {

    public IglooObj(GameTeam gameTeam) {
        super(ObjectiveType.IGLOO, Rarity.EASY, "Où est le pingouin ?",
                "Entrer dans un Igloo.", null,
                new ItemStack(Material.SNOW_BLOCK), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInIgloo(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof IglooStructure) complete(e.getPlayer());
    }
}
