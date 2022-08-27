package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class BrewingStandObj extends Objective {

    public BrewingStandObj(GameTeam gameTeam) {
        super(ObjectiveType.BREWING_STAND, Rarity.EASY, "Jesse, we need to cook",
                "Ouvrir un Alambic.", null,
                new ItemStack(Material.BREWING_STAND), false, gameTeam);
    }

    @EventHandler
    public void onTableOpen(final InventoryOpenEvent e){
        Player player = (Player) e.getPlayer();
        if(isInTeam(player) && e.getInventory().getType() == InventoryType.BREWING) complete(player);
    }
}
