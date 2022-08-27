package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

public class BreedMasterObj extends TeamObjective<Integer> {

    public BreedMasterObj(GameTeam gameTeam) {
        super(ObjectiveType.BREED_MASTER, Rarity.MODERATE, "Ambianceur du soir",
                "Reproduire des animaux 18 fois.",
                "Ne compte pas les animaux à oeuf.",
                new ItemStack(Material.WHEAT), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 18);
    }

    @EventHandler
    public void onBreed(final EntityBreedEvent e){
        if(e.getBreeder() instanceof Player player && isInTeam(player)) objectiveData.addProgress(player, 1);
    }
}
