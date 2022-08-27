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

public class BlazeKillObj extends TeamObjective<Integer> {

    public BlazeKillObj(GameTeam gameTeam) {
        super(ObjectiveType.BLAZE_KILL, Rarity.MODERATE, "Allumez le feu",
                "Tuer 10 Blazes.", null, new ItemStack(Material.BLAZE_ROD), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 10);
    }

    @EventHandler
    public void onBlazeKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.BLAZE && isInTeam(e.getEntity().getKiller()))
            objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}
