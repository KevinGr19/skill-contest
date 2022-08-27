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

public class CraftingTableObj extends Objective {

    public CraftingTableObj(GameTeam gameTeam) {
        super(ObjectiveType.CRAFTING_TABLE, Rarity.EASY, "Un destin tout tracé",
                "Ouvrir un Établi.", null,
                new ItemStack(Material.CRAFTING_TABLE), false, gameTeam);
    }

    @EventHandler
    public void onTableOpen(final InventoryOpenEvent e){
        Player player = (Player) e.getPlayer();
        if(isInTeam(player) && e.getInventory().getType() == InventoryType.WORKBENCH) complete(player);
    }
}
