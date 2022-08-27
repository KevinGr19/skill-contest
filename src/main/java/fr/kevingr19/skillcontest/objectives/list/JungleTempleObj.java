package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class JungleTempleObj extends Objective {

    public JungleTempleObj(GameTeam gameTeam) {
        super(ObjectiveType.JUNGLE_TEMPLE, Rarity.EASY, "La Cité d'Or",
                "Entrer dans un Temple de la Jungle.", null,
                new ItemStack(Material.MOSSY_COBBLESTONE), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInTemple(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof JungleTempleStructure) complete(e.getPlayer());
    }
}
