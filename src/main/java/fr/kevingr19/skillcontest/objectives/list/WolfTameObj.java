package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.inventory.ItemStack;

public class WolfTameObj extends Objective {

    public WolfTameObj(GameTeam gameTeam) {
        super(ObjectiveType.WOLF_TAME, Rarity.EASY, "Bon toutou.",
                "Apprivoiser un loup.", null,
                new ItemStack(Material.BONE), false, gameTeam);
    }

    @EventHandler
    public void onTame(final EntityTameEvent e){
        if(e.getEntity().getType() == EntityType.WOLF && e.getOwner() instanceof Player player && isInTeam(player)) complete(player);
    }
}
