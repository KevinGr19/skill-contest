package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.MaxChargeAnchorEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AnchorChargeObj extends Objective {

    public AnchorChargeObj(GameTeam gameTeam) {
        super(ObjectiveType.ANCHOR_CHARGE, Rarity.DIFFICULT, "My Main Goal, is to Blow Up",
                "Charger une Ancre de réapparition au maximum.", null,
                new ItemStack(Material.RESPAWN_ANCHOR), false, gameTeam);
    }

    @EventHandler
    public void onCharge(final MaxChargeAnchorEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
