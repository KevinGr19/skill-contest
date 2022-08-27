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

public class FarmMobsKillObj extends TeamObjective<Integer> {

    public FarmMobsKillObj(GameTeam gameTeam) {
        super(ObjectiveType.FARM_MOBS_KILL, Rarity.EASY, "C'est de l'industriel",
                "Tuer 30 animaux d'élevages (vaches,\npoulets, cochons, moutons.",
                null, new ItemStack(Material.CHICKEN), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 30);
    }

    @EventHandler
    public void onMobKill(final EntityDeathEvent e){
        if(isInTeam(e.getEntity().getKiller()) && (e.getEntityType() == EntityType.COW || e.getEntityType() == EntityType.CHICKEN || e.getEntityType() == EntityType.PIG ||
                e.getEntityType() == EntityType.SHEEP)) objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}
