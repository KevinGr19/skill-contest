package fr.kevingr19.skillcontest.objectives;

import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListData extends ObjectiveData<String>{

    protected static final int WRAP_LINE_LENGTH = 35;

    protected final String[] goals;
    protected final Set<String> remaining;
    protected int wrap_line_length;

    public ListData(Objective objective, boolean showProgress, String... goals){
        super(objective, showProgress);
        this.goals = goals.clone();
        this.wrap_line_length = WRAP_LINE_LENGTH;

        remaining = new HashSet<>();
        resetProgress();
    }

    public ListData(Objective objective, boolean showProgress, int wrap_line_length, String... goals){
        super(objective, showProgress);
        this.goals = goals.clone();
        this.wrap_line_length = wrap_line_length;

        remaining = new HashSet<>();
        resetProgress();
    }

    @Override
    public void resetProgress() {
        remaining.addAll(Arrays.asList(goals));
    }

    @Override
    public void addProgress(OfflinePlayer player, String progress) {
        if(objective.isDone() || !remaining.contains(progress)) return;
        remaining.remove(progress);

        if(remaining.size() == 0) objective.complete(player);
        if(showProgress && player.isOnline()) sendProgressMsg(player.getPlayer(), progress + " | " + getProgress());
    }

    @Override
    public String getProgress() {
        return (goals.length - remaining.size()) + "/" + goals.length;
    }

    @Override
    public String getExtraText(){
        StringBuilder text = new StringBuilder("\n\n");
        int length = 0;

        for(int i = 0; i < goals.length; i++)
        {
            if(length >= wrap_line_length){
                text.append("\n");
                length = 0;
            }
            else if(i != 0){
                text.append("§7 - ");
                length += 3;
            }

            text.append(remaining.contains(goals[i]) ? "§f" : "§a");
            text.append(goals[i]);
            length += goals[i].length();
        }

        return text.toString();
    }
}
