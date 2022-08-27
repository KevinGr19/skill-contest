package fr.kevingr19.skillcontest.objectives;

import fr.kevingr19.skillcontest.game.GameTeam;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * {@link Objective} with an {@link ObjectiveData} component attached to it.
 * Allow teams to combine player progress.
 */

abstract public class TeamObjective<T> extends Objective{

    protected final ObjectiveData<T> objectiveData;
    abstract protected ObjectiveData<T> createObjectiveData();

    public TeamObjective(ObjectiveType type, Objective.Rarity rarity, String name, String description, String notabene, ItemStack logo, GameTeam gameTeam){
        super(type, rarity, name, description, notabene, logo, true, gameTeam);
        this.objectiveData = createObjectiveData();
    }

    @Override
    protected String getProgression(UUID uuid){
        return (isDone() ? "§fFini par : §a" + solverName : "§fProgression : §c" + objectiveData.getProgress())
                + "\n§fCommun : §aOui" + objectiveData.getExtraText();
    }

}
