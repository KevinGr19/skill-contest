package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class StrongholdObj extends Objective {

    public StrongholdObj(GameTeam gameTeam) {
        super(ObjectiveType.STRONGHOLD, Rarity.EASY, "Bunker Suisse",
                "Entrer dans un Stronghold.", null,
                new ItemStack(Material.MOSSY_STONE_BRICKS), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInStronghold(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof StrongholdStructure) complete(e.getPlayer());
    }
}
