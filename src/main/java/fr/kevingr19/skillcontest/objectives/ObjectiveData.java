package fr.kevingr19.skillcontest.objectives;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.utils.Broadcast;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

abstract public class ObjectiveData<T> {

    public final Objective objective;
    public final boolean showProgress;

    public ObjectiveData(Objective objective, boolean showProgress){
        this.objective = objective;
        this.showProgress = showProgress;
    }

    abstract public void resetProgress();
    abstract public void addProgress(OfflinePlayer player, T progress);
    public void sendProgressMsg(Player player, String ratio){
        Broadcast.sendActionBar(player, String.format(Texts.OBJ_PROGRESS, objective.buttonName, ratio));
    }
    abstract public String getProgress();
    public String getExtraText(){
        return "";
    }

}
