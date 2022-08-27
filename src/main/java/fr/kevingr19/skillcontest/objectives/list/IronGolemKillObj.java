package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class IronGolemKillObj extends Objective {

    public IronGolemKillObj(GameTeam gameTeam) {
        super(ObjectiveType.IRON_GOLEM_KILL, Rarity.EASY, "CRS à terre",
                "Tuer un Golem de Fer naturel.", null,
                new ItemStack(Material.IRON_BLOCK), false, gameTeam);
    }

    @EventHandler
    public void onGolemKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.IRON_GOLEM && !((IronGolem) e.getEntity()).isPlayerCreated() &&
                isInTeam(e.getEntity().getKiller())) complete(e.getEntity().getKiller());
    }
}