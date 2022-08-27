package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class EnchantTableObj extends Objective {

    public EnchantTableObj(GameTeam gameTeam) {
        super(ObjectiveType.ENCHANT_TABLE, Rarity.EASY, "Merlin l'Enchanteur",
                "Fabriquer une table d'enchantement.", null,
                new ItemStack(Material.ENCHANTING_TABLE), false, gameTeam);
    }

    @EventHandler
    public void onTableCraft(final ResultItemTakenEvent e){
        if(isInTeam(e.getPlayer()) && e.getResult().getType() == Material.ENCHANTING_TABLE) complete(e.getPlayer());
    }
}
