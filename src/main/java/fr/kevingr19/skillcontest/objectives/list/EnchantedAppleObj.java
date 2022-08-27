package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantedAppleObj extends Objective {

    public EnchantedAppleObj(GameTeam gameTeam) {
        super(ObjectiveType.ENCHANTED_APPLE, Rarity.EASY, "Le péché d'Adam (lol)",
                "Manger une pomme en or enchantée.", null,
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), false, gameTeam);
    }

    @EventHandler
    public void onAppleEat(final PlayerItemConsumeEvent e){
        if(isInTeam(e.getPlayer()) && e.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) complete(e.getPlayer());
    }
}
