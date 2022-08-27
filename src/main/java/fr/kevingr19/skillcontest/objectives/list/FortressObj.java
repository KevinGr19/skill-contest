package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class FortressObj extends Objective {

    public FortressObj(GameTeam gameTeam) {
        super(ObjectiveType.FORTRESS, Rarity.EASY, "Los Angeles",
                "Entrer dans une forteresse du Nether.", null,
                new ItemStack(Material.NETHER_BRICK_FENCE), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInFortress(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof NetherFortressStructure) complete(e.getPlayer());
    }
}
