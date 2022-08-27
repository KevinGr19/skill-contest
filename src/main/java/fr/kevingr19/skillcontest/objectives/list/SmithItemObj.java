package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class SmithItemObj extends Objective {

    public SmithItemObj(GameTeam gameTeam) {
        super(ObjectiveType.SMITH_ITEM, Rarity.DIFFICULT, "Nano-carbone",
                "Améliorer un objet en diamant, en netherite.", null,
                new ItemStack(Material.NETHERITE_INGOT), false, gameTeam);
    }

    @EventHandler
    public void onSmith(final ResultItemTakenEvent e){
        if(e.getInventory().getType() == InventoryType.SMITHING && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
