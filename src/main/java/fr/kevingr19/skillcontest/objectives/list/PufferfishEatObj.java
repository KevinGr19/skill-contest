package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PufferfishEatObj extends Objective {

    public PufferfishEatObj(GameTeam gameTeam) {
        super(ObjectiveType.PUFFERFISH_EAT, Rarity.EASY, "Légende Japonaise",
                "Manger un poisson-globe.", null,
                new ItemStack(Material.PUFFERFISH), false, gameTeam);
    }

    @EventHandler
    public void onPufferfishEat(final PlayerItemConsumeEvent e){
        if(e.getItem().getType() == Material.PUFFERFISH && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
