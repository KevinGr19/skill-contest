package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class JebKillObj extends Objective {

    public JebKillObj(GameTeam gameTeam) {
        super(ObjectiveType.JEB_KILL, Rarity.EASY, "Rip LGBT",
                "Tuer un mouton renommé \"jeb_\".", null,
                new ItemStack(Material.RED_WOOL), false, gameTeam);
    }

    @EventHandler
    public void onSheepKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.SHEEP && "jeb_".equals(e.getEntity().getCustomName())
                && isInTeam(e.getEntity().getKiller())) complete(e.getEntity().getKiller());
    }
}