package fr.kevingr19.skillcontest.objectives;

import org.bukkit.OfflinePlayer;

public class TravelData extends ObjectiveData<Double>{

    private final double goal;
    private double progress = 0;

    public TravelData(Objective objective, boolean showProgress, double goal){
        super(objective, showProgress);
        this.goal = goal;
    }

    @Override
    public void resetProgress() {
        progress = 0;
    }

    @Override
    public void addProgress(OfflinePlayer player, Double progress) {
        if(objective.isDone() || progress == 0) return;
        this.progress += progress;

        if(goal <= this.progress){
            this.progress = goal;
            objective.complete(player);
        }
        if(showProgress && player.isOnline()) sendProgressMsg(player.getPlayer(), String.format("+%.0f | ", progress) + getProgress());
    }

    @Override
    public String getProgress() {
        return String.format("%.0f/%.0f", progress, goal);
    }
}
