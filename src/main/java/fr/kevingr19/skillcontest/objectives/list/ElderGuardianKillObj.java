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

public class ElderGuardianKillObj extends TeamObjective<Integer> {

    public ElderGuardianKillObj(GameTeam gameTeam) {
        super(ObjectiveType.ELDER_GUARDIAN_KILL, Rarity.DIFFICULT, "C'est le regard de la haine",
                "Tuer 3 Elder Guardian.", null,
                new ItemStack(Material.SPONGE), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 3);
    }

    @EventHandler
    public void onElderGuardianKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.ELDER_GUARDIAN && isInTeam(e.getEntity().getKiller()))
            objectiveData.addProgress(e.getEntity().getKiller(), 1);
    }
}