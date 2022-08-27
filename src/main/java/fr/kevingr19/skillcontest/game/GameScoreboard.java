package fr.kevingr19.skillcontest.game;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScoreboard {

    private final Scoreboard scoreboard;
    private final Objective mainObj;

    private final Map<GameTeam.Color, Team> teamScores = new HashMap<>();

    public GameScoreboard(String title){
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        mainObj = scoreboard.registerNewObjective("game", "dummy", title);
        mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Objective healthObj = scoreboard.registerNewObjective("health", "health", "§c♥");
        healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public boolean isShown(GameTeam.Color color){
        return teamScores.containsKey(color);
    }

    public void createLines(List<GameTeam> teams){
        int line = 9;
        mainObj.getScore(" §a").setScore(line--);

        for(GameTeam gameTeam : teams)
        {
            GameTeam.Color color = gameTeam.color();

            Team counter = scoreboard.registerNewTeam(color.name() + "Counter");
            counter.addEntry(""+color.colorPrefix);
            teamScores.put(color, counter);

            mainObj.getScore(""+color.colorPrefix).setScore(line--);
        }
        mainObj.getScore(" §b").setScore(line);

        updateTeamScores();
    }

    public void updateTeamScores(){
        int maxPoints = 0;
        List<GameTeam.Color> topTeams = new ArrayList<>();

        for(GameTeam.Color color : teamScores.keySet())
        {
            int points = Game.teams().getTeam(color).getPoints();

            if(points == maxPoints) topTeams.add(color);
            else if(points > maxPoints)
            {
                for(GameTeam.Color team : topTeams) updateTeamScore(team, "§f" + maxPoints);
                topTeams.clear();
                topTeams.add(color);
                maxPoints = points;
            }
            else updateTeamScore(color, "§f" + points);
        }

        for(GameTeam.Color color : topTeams) updateTeamScore(color, "§e" + maxPoints);
    }

    private void updateTeamScore(GameTeam.Color color, String text){
        if(!isShown(color)) return;

        Team counter = teamScores.get(color);
        counter.setPrefix("§7- " + color.colorPrefix + color.simpleName + " §r: " + text);
    }

    public void hidePoints(){
        teamScores.keySet().forEach(color -> updateTeamScore(color, "§7???"));
    }
}
