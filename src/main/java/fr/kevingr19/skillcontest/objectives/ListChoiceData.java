package fr.kevingr19.skillcontest.objectives;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class ListChoiceData extends ListData{

    protected final List<String> done;
    protected final int minimum;

    public ListChoiceData(Objective objective, boolean showProgress, int minimum, String... goals){
        super(objective, showProgress, goals);
        this.done = new ArrayList<>();
        this.minimum = minimum;
    }

    public void resetProgress(){
        super.resetProgress();
        if(done != null) done.clear();
    }

    @Override
    public void addProgress(OfflinePlayer player, String progress) {
        if(objective.isDone() || !remaining.contains(progress)) return;
        done.add(progress);
        remaining.remove(progress);

        if(done.size() >= minimum) objective.complete(player);
        if(showProgress && player.isOnline()) sendProgressMsg(player.getPlayer(), progress + " | " + getProgress());
    }

    @Override
    public String getProgress() {
        return done.size() + "/" + minimum;
    }

    @Override
    public String getExtraText(){
        StringBuilder text = new StringBuilder("\n\n§fAccomplis : ");
        int length = 12;

        if(done.size() == 0){
            text.append("Aucun");
            return text.toString();
        }

        for(int i = 0; i < done.size(); i++)
        {
            if(length >= WRAP_LINE_LENGTH) {
                text.append("\n");
                length = 0;
            }
            else if(i != 0){
                text.append("§7 - ");
                length += 3;
            }

            text.append("§a" + done.get(i));
            length += done.get(i).length();
        }

        return text.toString();
    }
}