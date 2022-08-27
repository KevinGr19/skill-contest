package fr.kevingr19.skillcontest.objectives;

import org.bukkit.OfflinePlayer;

public class CounterData extends ObjectiveData<Integer>{

    private final int goal;
    private int progress = 0;

    public CounterData(Objective objective, boolean showProgress, int goal){
        super(objective, showProgress);
        this.goal = goal;
    }

    @Override
    public void resetProgress() {
        progress = 0;
    }

    public void addProgress(OfflinePlayer player, Integer progress) {
        if(objective.isDone()) return;
        this.progress += progress;

        if(goal <= this.progress){
            this.progress = goal;
            objective.complete(player);
        }
        if(showProgress && player.isOnline()) sendProgressMsg(player.getPlayer(), getProgress());
    }

    @Override
    public String getProgress() {
        return progress + "/" + goal;
    }
}
