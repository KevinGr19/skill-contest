package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.inventory.ItemStack;

public class BerryStepObj extends Objective {

    public BerryStepObj(GameTeam gameTeam) {
        super(ObjectiveType.BERRY_STEP, Rarity.EASY, "Mémé dans les hortis",
                "Marcher dans des baies sauvages.", null,
                new ItemStack(Material.SWEET_BERRIES), false, gameTeam);
    }

    @EventHandler
    public void onBerryDamage(final EntityDamageByBlockEvent e){
        if(e.getDamager() == null || e.getDamager().getType() != Material.SWEET_BERRY_BUSH) return;
        if(e.getEntity() instanceof Player player && isInTeam(player)) complete(player);
    }
}
