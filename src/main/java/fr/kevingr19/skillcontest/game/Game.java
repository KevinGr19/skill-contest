package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Sounds;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.event.listener.EntityListener;
import fr.kevingr19.skillcontest.event.listener.ItemListener;
import fr.kevingr19.skillcontest.event.listener.MessageListener;
import fr.kevingr19.skillcontest.utils.Broadcast;
import fr.kevingr19.skillcontest.utils.SoundComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Class that stores everything related to the game (teams, players, settings, timers),
 * plus game logic.
 */

public class Game{

    private static final Game inst = new Game(State.NONE);

    public static Game inst(){
        return inst;
    }
    public static GameScoreboard scoreboard(){
        return inst.scoreboard;
    }
    public static GameTeamManager teams(){
        return inst.teamManager;
    }
    public static GameTimer timer(){
        return inst.timer;
    }

    /**
     * Represents the state of the {@link Game}.
     */
    public enum State {NONE, WAIT, START, RUN, END}

    private final GameScoreboard scoreboard;
    private final GameTeamManager teamManager;
    private final GameTimer timer = new GameTimer();

    private final GamePvPHandler pvpHandler = new GamePvPHandler();
    private final ItemListener itemListener = new ItemListener();
    private final EntityListener entityListener = new EntityListener();

    private final Map<UUID, GamePlayer> players = new HashMap<>();

    private UUID host;
    private World baseWorld;

    private State state;

    private Game(State state){
        this.state = state;
        scoreboard = new GameScoreboard(Texts.SCOREBOARD_NAME);
        teamManager = new GameTeamManager(scoreboard);
    }

    public GamePvPHandler getPvpHandler(){
        return pvpHandler;
    }

    public boolean isHost(UUID uuid){
        return uuid.equals(host);
    }
    public boolean isHost(Player player){
        return player.getUniqueId().equals(host);
    }
    public void setHost(@Nonnull Player player){
        Player oldHost = Bukkit.getPlayer(host);

        host = player.getUniqueId();
        Main.inst().pingRunnable.hostName = player.getName();
        if(isState(State.WAIT)) LobbyHandler.inst().setPlayerHotbar(player);

        if(oldHost != null){
            oldHost.sendMessage(Texts.PLUGIN_MSG + "Vous n'êtes désormais plus le host de la partie.");
            if(isState(State.WAIT)) LobbyHandler.inst().setPlayerHotbar(oldHost);
        }

        player.sendMessage(Texts.PLUGIN_MSG + "Vous êtes désormais le host. Tapez '/skc help' pour plus d'informations.");
    }

    public boolean isPlaying(Player player, boolean withTeam){
        return isPlaying(player.getUniqueId(), withTeam);
    }
    public boolean isPlaying(UUID uuid, boolean withTeam){
        return players.containsKey(uuid) && (!withTeam || players.get(uuid).hasGameTeam());
    }
    public boolean isPlaying(String name, boolean withTeam){
        for(GamePlayer gamePlayer : players.values()) if(gamePlayer.getName().equals(name))
            return !withTeam || gamePlayer.hasGameTeam();
        return false;
    }

    @Nullable
    public GamePlayer getGamePlayer(Player player){
        return players.getOrDefault(player.getUniqueId(), null);
    }
    @Nullable
    public GamePlayer getGamePlayer(UUID uuid){
        return players.getOrDefault(uuid, null);
    }
    @Nullable
    public GamePlayer getGamePlayer(String name){
        for(GamePlayer gamePlayer : players.values()) if(gamePlayer.getName().equals(name)) return gamePlayer;
        return null;
    }

    public void addGamePlayer(Player player, @Nonnull GameTeam gameTeam){
        if(isPlaying(player, false)) return;

        GamePlayer gamePlayer = new GamePlayer(player.getName(), player.getUniqueId());
        players.put(player.getUniqueId(), gamePlayer);
        gamePlayer.setGameTeam(gameTeam);
        gamePlayer.sendTeamChangeMessage(player);
    }

    public boolean isState(State state){
        return this.state == state;
    }
    public boolean isStates(State... states){
        return Arrays.stream(states).anyMatch(_state -> _state == state);
    }
    public boolean hasStarted(){
        return state != State.NONE && state != State.WAIT;
    }

    public World getBaseWorld(){
        return baseWorld;
    }

    public void initGame(Player player){
        if(!isState(State.NONE)) return;

        setHost(player);

        Bukkit.getOnlinePlayers().forEach(online -> {
            online.setScoreboard(scoreboard.getScoreboard());
            timer.getTimeBar().addPlayer(online);
        });

        baseWorld = player.getWorld();
        LobbyHandler.activate(baseWorld);
        Bukkit.getPluginManager().registerEvents(new MessageListener(), Main.inst());

        state = State.WAIT;
        Bukkit.broadcastMessage(Texts.BROADCAST + "§aLa partie est désormais en phase d'attente.");

    }

    public void runChecksToStart(){
        CommandSender sender = Bukkit.getPlayer(host);

        int teams = 0;
        for(GameTeam.Color color : GameTeam.Color.values()){
            GameTeam gameTeam = teamManager.getTeam(color);
            if(!gameTeam.isActive() || gameTeam.getMemberCount() == 0) continue;
            teams++;

            if(teams >= 2){
                startGame();
                return;
            }
        }

        sender.sendMessage(Texts.ERROR + "Il n'y a pas assez d'équipes inscrites.");
    }

    private void startGame(){
        if(!isState(State.WAIT)) return;

        state = State.START;
        LobbyHandler.deactivate(true);

        // Clean players map and spawn game-players
        players.forEach((uuid, gamePlayer) -> {
            if(!gamePlayer.hasGameTeam()) {
                players.remove(uuid);
                return;
            }

            Player player = Bukkit.getPlayer(uuid);
            if(player != null && !player.isDead()) gamePlayer.spawnPlayer(player, false);
        });

        // Set non-playing players as spectators
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(!players.containsKey(player.getUniqueId())) player.setGameMode(GameMode.SPECTATOR);
        });

        // Fetch all playing teams
        List<GameTeam> teams = new ArrayList<>();
        for(GameTeam.Color color : GameTeam.Color.values())
        {
            GameTeam team = teamManager.getTeam(color);
            if(team.isActive() && team.getMemberCount() > 0){
                teamManager.setTeamPlaying(team);
                team.startObjectives();
                teams.add(team);
            }
        }

        scoreboard.createLines(teams);
        timer.runTaskTimer(Main.inst(), 20, 20);

        startListeners();
        Main.inst().setupWorlds();
        Main.inst().pingRunnable.pvp = "§cOff";

        state = State.RUN;

        Bukkit.broadcastMessage(Texts.GAME_START);
        Broadcast.broadcastSound(Sound.ENTITY_WITHER_SPAWN, 1, 0.2f);
    }



    private void startListeners(){
        Bukkit.getPluginManager().registerEvents(pvpHandler, Main.inst());
        Bukkit.getPluginManager().registerEvents(itemListener, Main.inst());

        entityListener.trackAllPlayerFall();
        entityListener.getPlayersBiome();
        Bukkit.getPluginManager().registerEvents(entityListener, Main.inst());
    }

    private void stopListeners(){
        HandlerList.unregisterAll(pvpHandler);
        HandlerList.unregisterAll(itemListener);
        HandlerList.unregisterAll(entityListener);
    }

    private boolean pointsHidden = false;
    public boolean arePointsHidden(){
        return pointsHidden;
    }

    public void hidePoints(){
        if(pointsHidden) return;

        pointsHidden = true;
        scoreboard.hidePoints();

        Bukkit.broadcastMessage(Texts.BROADCAST + "Les points sont désormais §7cachés§r jusqu'à la fin de la partie.");
        Broadcast.broadcastSound(Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1, 1);
    }

    public void stopGame(){
        if(state != State.RUN) return;

        state = State.END;
        teamManager.getPlayingTeams().forEach(GameTeam::stopObjectives);

        stopListeners();
        timer.getTimeBar().removeAll();
        Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SPECTATOR));

        if(!pointsHidden){
            declareWinners();
            return;
        }

        // Heartbeat Runnable
        new BukkitRunnable(){

            private int total = 9;
            private int time = total;

            @Override
            public void run()
            {
                if(time <= 0) {
                    declareWinners();
                    cancel();
                    return;
                }

                else if(time % 2 == 0){
                    Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 0.8f, 0.7f));
                }

                time--;

            }}.runTaskTimer(Main.inst(), 0, 20);
    }

    private void declareWinners(){

        List<GameTeam> winners = new ArrayList<>();
        int max = 0;

        for(GameTeam.Color color : GameTeam.Color.values())
        {
            GameTeam team = teamManager.getTeam(color);
            if(!team.isPlaying()) continue;

            if(team.getPoints() > max){
                winners.clear();
                winners.add(team);
                max = team.getPoints();
            }

            else if(team.getPoints() == max) winners.add(team);
        }

        winners.forEach(GameTeam::setWinner);
        displayWin(winners, max);

        pointsHidden = false;
        scoreboard.updateTeamScores();
    }

    private void displayWin(final List<GameTeam> winners, final int maxPoints){

        String winTitle, subTitle;
        SoundComponent winSound;

        if(winners.size() == 1){
            winTitle = Texts.WIN_TITLE;
            subTitle = String.format(Texts.ONE_WINNER_SUBTITLE, winners.get(0).color().displayName, maxPoints);
            winSound = Sounds.WINNER;
        }
        else{
            winTitle = Texts.DRAW_TITLE;
            subTitle = String.format(Texts.DRAW_SUBTITLE, maxPoints);
            winSound = Sounds.DRAW;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            GamePlayer gamePlayer = Game.inst.getGamePlayer(player);

            if(gamePlayer == null || !gamePlayer.hasGameTeam() || !gamePlayer.getGameTeam().isPlaying()) {
                player.sendTitle(Texts.SPECTATOR_TITLE, subTitle, 10, 200, 20);
                Sounds.END_SPECTATOR.playSound(player);
            }
            else if(gamePlayer.getGameTeam().hasWon()) {
                player.sendTitle(winTitle, subTitle, 30, 200, 40);
                winSound.playSound(player);
            }
            else{
                player.sendTitle(Texts.LOSE_TITLE, subTitle, 10, 200, 40);
                Sounds.LOSER.playSound(player);
            }
        });

    }

}
