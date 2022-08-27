package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ZoglinKillObj extends Objective {

    public ZoglinKillObj(GameTeam gameTeam) {
        super(ObjectiveType.ZOGLIN_KILL, Rarity.MODERATE, "Délocalisation.",
                "Tuer un Zoglin.", null, new ItemStack(Material.ZOGLIN_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onZoglinKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.ZOGLIN && isInTeam(e.getEntity().getKiller())) complete(e.getEntity().getKiller());
    }
}