package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * Class that manages the teams of a {@link Game}.
 */

public class GameTeamManager {

    public static final int MAX_PLAYER_LIMIT = 20;

    private final Map<GameTeam.Color, GameTeam> gameTeams = new HashMap<>();
    private final Set<GameTeam> playingTeams = new HashSet<>();

    private boolean playerChoose = true;
    private int playerLimit = 0;

    public GameTeamManager(GameScoreboard scoreboard){

        for(GameTeam.Color color : GameTeam.Color.values())
        {
            Team team = scoreboard.getScoreboard().registerNewTeam(color.toString().toLowerCase());
            team.setColor(color.colorPrefix);
            team.setDisplayName(color.displayName);

            GameTeam gameTeam = new GameTeam(color, team, ItemUtil.create(color.banner, 1, color.displayName));
            gameTeams.put(color, gameTeam);
        }

    }

    public int getPlayerLimit(){
        return playerLimit;
    }
    public void setPlayerLimit(int playerLimit){
        this.playerLimit = Math.max(playerLimit, 0);
    }
    public boolean noPlayerLimit(){
        return playerLimit < 1;
    }

    public boolean canPlayerChoose(){
        return playerChoose;
    }
    public void setPlayerChoice(boolean can){
        playerChoose = can;
    }

    public GameTeam getTeam(GameTeam.Color color){
        return gameTeams.get(color);
    }

    public void setTeamPlaying(GameTeam gameTeam){
        if(playingTeams.contains(gameTeam)) return;
        gameTeam.setPlaying(true);
        playingTeams.add(gameTeam);
    }
    public List<GameTeam> getPlayingTeams(){
        return playingTeams.stream().toList();
    }

}
