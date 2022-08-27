package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ZombiePiglinObj extends TeamObjective<Integer> {

    public ZombiePiglinObj(GameTeam gameTeam) {
        super(ObjectiveType.ZOMBIE_PIGLIN_KILL, Rarity.IMPOSSIBLE, "Acharnement",
                "Tuer 120 Zombie-Piglin.", null, new ItemStack(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, false, 120);
    }

    @EventHandler
    public void onZombiePiglinKill(final EntityDeathEvent e){
        if(e.getEntity().getType() == EntityType.ZOMBIFIED_PIGLIN && isInTeam(e.getEntity().getKiller()))
            objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}
