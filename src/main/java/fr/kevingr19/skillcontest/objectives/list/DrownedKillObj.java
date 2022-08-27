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

public class DrownedKillObj extends TeamObjective<Integer> {

    public DrownedKillObj(GameTeam gameTeam) {
        super(ObjectiveType.DROWNED_KILL, Rarity.MODERATE, "Swimming Dead",
                "Tuer 10 Noyés.", null,
                new ItemStack(Material.DROWNED_SPAWN_EGG), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 10);
    }

    @EventHandler
    public void onDrownedKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.DROWNED && isInTeam(e.getEntity().getKiller()))
            objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}
