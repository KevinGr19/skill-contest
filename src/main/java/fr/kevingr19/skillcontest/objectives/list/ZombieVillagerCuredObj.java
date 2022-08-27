package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.inventory.ItemStack;

public class ZombieVillagerCuredObj extends Objective {

    public ZombieVillagerCuredObj(GameTeam gameTeam) {
        super(ObjectiveType.VILLAGER_CURED, Rarity.MODERATE, "PhD, PhD & PhD.",
                "Soigner un zombie-villageois.", null,
                new ItemStack(Material.ZOMBIE_VILLAGER_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onVillagerCure(final EntityTransformEvent e){
        if(e.getEntity().getType() != EntityType.ZOMBIE_VILLAGER) return;

        ZombieVillager zombieVillager = (ZombieVillager) e.getEntity();
        if(isInTeam(zombieVillager.getConversionPlayer())) complete(zombieVillager.getConversionPlayer());
    }
}
