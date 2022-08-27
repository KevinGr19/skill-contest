package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInventoryNewItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class DiamondObj extends Objective {

    public DiamondObj(GameTeam gameTeam) {
        super(ObjectiveType.DIAMOND, Rarity.EASY, "X-Ray",
                "Obtenir un diamant.", null,
                new ItemStack(Material.DIAMOND), false, gameTeam);
    }

    @EventHandler
    public void onObtainItem(final PlayerInventoryNewItemEvent e){
        if(e.getMaterial() == Material.DIAMOND && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
