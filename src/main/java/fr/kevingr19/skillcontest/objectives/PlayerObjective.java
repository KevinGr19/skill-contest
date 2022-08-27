package fr.kevingr19.skillcontest.objectives;

import fr.kevingr19.skillcontest.game.GameTeam;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
>>>>>>> bb6c13e8b9991e60589eac36b39ae084fa7ee097

abstract public class PlayerObjective<T> extends Objective{

    protected final Map<UUID, ObjectiveData<T>> objectiveDataMap = new HashMap<>();
    abstract protected void addObjectiveData(UUID uuid);

    public PlayerObjective(ObjectiveType type, Rarity rarity, String name, String description, String notabene, ItemStack logo, GameTeam gameTeam) {
        super(type, rarity, name, description, notabene, logo, false, gameTeam);
    }

    protected ObjectiveData<T> getObjectiveData(UUID uuid){
        if(!objectiveDataMap.containsKey(uuid)) addObjectiveData(uuid);
        return objectiveDataMap.get(uuid);
    }

    @Override
    public String getProgression(UUID uuid){
        String progress;
        if(isDone()) progress = "§fFait : §a" + solverName;
        else{
            ObjectiveData<T> objData = getObjectiveData(uuid);
            if (objData == null) progress = "§fProgression : §c...";
            else progress = "§fProgression : §c" + objData.getProgress() + objData.getExtraText();
        }

        return progress + "\n§fCommun : §cNon";
    }
}