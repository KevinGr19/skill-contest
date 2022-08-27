package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BlueIceCraftObj extends Objective {

    public BlueIceCraftObj(GameTeam gameTeam) {
        super(ObjectiveType.BLUE_ICE_CRAFT, Rarity.MODERATE, "Zéro friction",
                "Fabriquer un bloc de glace bleue.",
                null, new ItemStack(Material.BLUE_ICE), false, gameTeam);
    }

    @EventHandler
    public void onBlueIceCraft(final ResultItemTakenEvent e){
        if(e.isCrafting() && e.getResult().getType() == Material.BLUE_ICE && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
