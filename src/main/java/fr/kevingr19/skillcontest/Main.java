package fr.kevingr19.skillcontest;

import fr.kevingr19.skillcontest.commands.GlobalMessageCommand;
import fr.kevingr19.skillcontest.commands.MainCommand;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GamePlayer;
import fr.kevingr19.skillcontest.game.LobbyHandler;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.list.BigMapObj;
import fr.kevingr19.skillcontest.utils.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Main class of the plugin SkillContest.
 */

public final class Main extends JavaPlugin implements Listener {

    private static Main inst;
    public static Main inst(){
        return inst;
    }
    public static StructureManager structureManager(){
        return inst.structureManager;
    }

    public static void print(String text){
        if(inst != null) inst.getLogger().info(text);
    }
    public static void error(String text){
        print("[Error] " + text);
    }

    public final PingRunnable pingRunnable = new PingRunnable();
    private final StructureManager structureManager = new StructureManager();

    @Override
    public void onEnable() {
        inst = this;
        print("Starting plugin...");

        getServer().getPluginCommand("skc").setExecutor(new MainCommand());
        getServer().getPluginCommand("gb").setExecutor(new GlobalMessageCommand());
        getServer().getPluginManager().registerEvents(this, this);

        pingRunnable.runTaskTimerAsynchronously(this, 0, 40);

        getServer().getPluginManager().registerEvents(structureManager, this);
        structureManager.initialize();
        structureManager.runTaskTimer(this, 20, 20);

        BigMapObj.loadImage(Texts.MAP_IMAGE);

        print("Objectifs : " + ObjectiveType.values().length);
        print("Plugin started successfully.");
    }

    @Override
    public void onDisable() {
        print("Stopping plugin...");

        if(LobbyHandler.isActive()) {
            LobbyHandler.deactivate(true);
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.getInventory().clear();
                player.setGameMode(Bukkit.getDefaultGameMode());
            });
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            if(Game.inst().isPlaying(player, true)) Game.inst().getGamePlayer(player).setGameTeam(null, false);
        });
        Game.timer().getTimeBar().removeAll();

        print("Plugin stopped.");
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.setPlayerListHeader(Texts.TAB_HEADER);

        if(!Game.inst().isState(Game.State.NONE))
        {
            player.setScoreboard(Game.scoreboard().getScoreboard());

            if(Game.inst().isState(Game.State.END)) player.setGameMode(GameMode.SPECTATOR);
            else Game.timer().getTimeBar().addPlayer(player);

            if(Game.inst().isPlaying(player, true)) {
                GamePlayer gamePlayer = Game.inst().getGamePlayer(player);
                gamePlayer.setName(player.getName());
                gamePlayer.updateName();

                if(Game.inst().hasStarted() && !gamePlayer.hasSpawned())
                    gamePlayer.spawnPlayer(player, true);
            }
            else if(Game.inst().hasStarted()) player.setGameMode(GameMode.SPECTATOR);
        }

        e.setJoinMessage(String.format(Texts.JOIN_MSG, player.getDisplayName()));
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent e){
        Player player = e.getPlayer();
        GamePlayer gamePlayer = Game.inst().getGamePlayer(player);

        if(Game.inst().hasStarted()) {
            if(gamePlayer == null)
                player.setGameMode(GameMode.SPECTATOR);

            else if(!gamePlayer.hasSpawned()) {
                e.setRespawnLocation(Game.inst().getBaseWorld().getSpawnLocation());
                gamePlayer.spawnPlayer(player, false);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onQuit(final PlayerQuitEvent e){
        if(!Game.inst().isState(Game.State.NONE)) Game.timer().getTimeBar().removePlayer(e.getPlayer());
        e.setQuitMessage(String.format(Texts.QUIT_MSG, e.getPlayer().getDisplayName()));
    }

    public void setupWorlds(){
        Bukkit.getWorlds().forEach(world -> {
            world.setFullTime(0);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        });
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(Game.inst().isState(Game.State.NONE) || e.getDeathMessage() == null) return;
        String msg = e.getDeathMessage().replace(e.getEntity().getName(), e.getEntity().getDisplayName() + "§r");

        Player killer = e.getEntity().getKiller();
        if(killer != null && !killer.getUniqueId().equals(e.getEntity().getUniqueId()))
            msg = msg.replace(killer.getName(), killer.getDisplayName() + "§r");

        e.setDeathMessage(msg);
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent e){
        if(Game.inst().hasStarted() && e.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP) e.setCancelled(true);
    }

    @EventHandler
    public void onBedClick(PlayerBedEnterEvent e){
        if(Game.inst().hasStarted() && e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK){
            new BukkitRunnable(){
                @Override
                public void run(){
                    e.getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 0);
                    Broadcast.sendActionBar(e.getPlayer(), Texts.NO_SLEEP);
                }
            }.runTaskLater(this, 0);
        }
    }

    /**
     * A {@link BukkitRunnable} running asynchronously, that displays the ping of players in the player list.
     */

    public static class PingRunnable extends BukkitRunnable{

        public String hostName;
        public String pvp;

        private PingRunnable(){
            hostName = pvp = "§7...";
        }

        @Override
        public void run() {
            Bukkit.getOnlinePlayers().forEach(player ->
                    player.setPlayerListFooter(String.format(Texts.PING_FOOTER, hostName, player.getPing(), pvp)));
        }
    }
}
