package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.inventory.ItemStack;

public class SculkShriekerTriggerObj extends Objective {

    public SculkShriekerTriggerObj(GameTeam gameTeam) {
        super(ObjectiveType.SCULK_SHRIEKER_TRIGGER, Rarity.EASY, "T'es un Homme Mort",
                "Activer un Hurleur Sculk.", null,
                new ItemStack(Material.SCULK_SHRIEKER), false, gameTeam);
    }

    @EventHandler
    public void onBlockEvent(final BlockReceiveGameEvent e){
        if(e.getBlock().getType() == Material.SCULK_SHRIEKER && e.getEntity() instanceof Player player && isInTeam(player))
            complete(player);
    }
}