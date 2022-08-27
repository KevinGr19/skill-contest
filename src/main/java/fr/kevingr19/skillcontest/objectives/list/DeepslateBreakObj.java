package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DeepslateBreakObj extends Objective {

    public DeepslateBreakObj(GameTeam gameTeam) {
        super(ObjectiveType.DEEPSLATE_BREAK, Rarity.EASY, "On touche le fond.",
                "Miner de l'Ardoise des Âbimes.", null,
                new ItemStack(Material.DEEPSLATE), false, gameTeam);
    }

    @EventHandler (ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent e){
        if(e.getBlock().getType() == Material.DEEPSLATE && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
