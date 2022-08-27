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

public class BruteKillObj extends TeamObjective<Integer> {

    public BruteKillObj(GameTeam gameTeam) {
        super(ObjectiveType.PIGLIN_BRUTE_KILL, Rarity.MODERATE, "Haram Pigs",
                "Tuer 4 Piglins Brutes.", null, new ItemStack(Material.GOLDEN_AXE), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 4);
    }

    @EventHandler
    public void onBruteKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.PIGLIN_BRUTE && isInTeam(e.getEntity().getKiller()))
            objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}
