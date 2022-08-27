package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class WaxedCopperBlockObj extends Objective {

    public WaxedCopperBlockObj(GameTeam gameTeam) {
        super(ObjectiveType.WAXED_COPPER_BLOCK, Rarity.MODERATE, "Jeunesse éternelle",
                "Poser un bloc de cuivre neuf ciré.", null,
                new ItemStack(Material.COPPER_BLOCK), false, gameTeam);
    }

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlace(final BlockPlaceEvent e){
        if(e.getBlockPlaced().getType() == Material.WAXED_COPPER_BLOCK || e.getBlockPlaced().getType() == Material.WAXED_CUT_COPPER && isInTeam(e.getPlayer()))
            complete(e.getPlayer());
    }
}
