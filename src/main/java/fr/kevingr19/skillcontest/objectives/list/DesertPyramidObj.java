package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class DesertPyramidObj extends Objective {

    public DesertPyramidObj(GameTeam gameTeam) {
        super(ObjectiveType.DESERT_PYRAMID, Rarity.EASY, "Indiana Jones",
                "Entrer dans un Temple du Désert.", null,
                new ItemStack(Material.CHISELED_RED_SANDSTONE), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInPyramid(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof DesertPyramidStructure) complete(e.getPlayer());
    }
}
