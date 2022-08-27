package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class MaxEnchantObj extends Objective {

    public MaxEnchantObj(GameTeam gameTeam) {
        super(ObjectiveType.MAX_ENCHANT, Rarity.DIFFICULT, "Over 9000",
                "Faire un enchantement niveau 30.",
                "Il doit être réalisé en une fois\ndans une table d'enchantement.",
                new ItemStack(Material.EXPERIENCE_BOTTLE), false, gameTeam);
    }

    @EventHandler
    public void onEnchant(final EnchantItemEvent e){
        if(isInTeam(e.getEnchanter()) && e.getExpLevelCost() == 30) complete(e.getEnchanter());
    }
}
