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

public class FoxKillObj extends TeamObjective<Integer> {

    public FoxKillObj(GameTeam gameTeam) {
        super(ObjectiveType.FOX_KILL, Rarity.MODERATE, "Furry Killer",
                "Tuer 4 Renards.", null, new ItemStack(Material.FOX_SPAWN_EGG), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 4);
    }

    @EventHandler
    public void onFoxKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.FOX && isInTeam(e.getEntity().getKiller()))
            objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}
