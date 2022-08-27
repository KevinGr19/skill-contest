package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class CaveSpawnerBreakObj extends Objective {

    public CaveSpawnerBreakObj(GameTeam gameTeam) {
        super(ObjectiveType.SPAWNER_BREAK, Rarity.EASY, "Baby Blues",
                "Détruire un spawner de zombie,\nsquelette ou d'araignée.", null,
                new ItemStack(Material.SPAWNER), false, gameTeam);
    }

    @EventHandler (ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent e){
        if(e.getBlock().getType() == Material.SPAWNER && isInTeam(e.getPlayer())){
            CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
            if(spawner.getSpawnedType() == EntityType.ZOMBIE || spawner.getSpawnedType() == EntityType.SKELETON || spawner.getSpawnedType() == EntityType.SPIDER)
                complete(e.getPlayer());
        }
    }
}
