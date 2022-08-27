package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class CauldronSnowObj extends Objective {

    public CauldronSnowObj(GameTeam gameTeam) {
        super(ObjectiveType.CAULDRON_SNOW, Rarity.EASY, "Plat froid",
                "Remplir un chaudron de neige.", null,
                new ItemStack(Material.POWDER_SNOW_BUCKET), false, gameTeam);
    }

    @EventHandler
    public void onCauldronFill(final CauldronLevelChangeEvent e){
        if(e.getNewState().getType() != Material.POWDER_SNOW_CAULDRON) return;
        if(e.getEntity() instanceof Player player && isInTeam(player)) complete(player);
    }
}
