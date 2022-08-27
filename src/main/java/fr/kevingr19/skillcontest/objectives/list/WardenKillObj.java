package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class WardenKillObj extends Objective {

    public WardenKillObj(GameTeam gameTeam) {
        super(ObjectiveType.WARDEN_KILL, Rarity.IMPOSSIBLE, "6:00 AM",
                "Tuer un Warden.", null,
                new ItemStack(Material.WARDEN_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onWardenKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.WARDEN && isInTeam(e.getEntity().getKiller())) complete(e.getEntity().getKiller());
    }
}