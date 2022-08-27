package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.utils.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    public static final int minGameDuration = 1800;
    public static final int maxGameDuration = 12 * 3600;

    private final BossBar timeBar;

    private int gameDuration;
    private int time;

    private int hidePointsTime = 1800;
    private int activatePvPTime = 1200;

    public GameTimer(){
        this.timeBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);

        gameDuration = time = 3 * 3600;
        updateBarTime();
    }

    public BossBar getTimeBar() {
        return timeBar;
    }

    public int getHidePointsTime() {
        return hidePointsTime;
    }
    public int getActivatePvPTime() {
        return activatePvPTime;
    }
    public int getGameDuration() {
        return gameDuration;
    }

    public void setHidePointsTime(int hidePointsTime) {
        if(!Game.inst().isState(Game.State.WAIT)) {
            Main.error("Hide points time can only be changed in State.WAIT");
            return;
        }
        this.hidePointsTime = Math.max(Math.min(hidePointsTime, gameDuration), 0);
    }
    public void setActivatePvPTime(int pvpTime) {
        if(!Game.inst().isState(Game.State.WAIT)) {
            Main.error("PvP activation time can only be changed in State.WAIT");
            return;
        }
        this.activatePvPTime = Math.max(Math.min(pvpTime, gameDuration), 0);
    }
    public void setGameDuration(int gameTime){
        if(!Game.inst().isState(Game.State.WAIT)) {
            Main.error("Game duration can only be changed in State.WAIT");
            return;
        }

        gameDuration = time = Math.max(Math.min(gameTime, maxGameDuration), minGameDuration);
        if(hidePointsTime > gameDuration) hidePointsTime = gameDuration;
        if(activatePvPTime > gameDuration) activatePvPTime = gameDuration;

        updateBarTime();
    }

    public void addHidePointsTime(int minutes){
        setHidePointsTime(hidePointsTime + minutes*60);
    }
    public void addActivatePvPTime(int minutes) {
        setActivatePvPTime(activatePvPTime + minutes*60);
    }
    public void addGameDuration(int minutes){
        setGameDuration(gameDuration + minutes*60);
    }

    @Override
    public void run(){
        time--;

        // Debug
        // End debug

        if(time < 3600) updateBarTime();
        else if(time % 60 == 59) updateBarTime();

        if(time <= 0){
            Game.inst().stopGame();
            cancel();
            return;
        }

        if(time <= 10){
            Broadcast.broadcastTitle("§c" + time, null, 5, 20, 0);
            Broadcast.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 0.5f);
            if(time == 2) Broadcast.broadcastSound(Sound.BLOCK_BELL_RESONATE, 1, 1);
        }

        if(time <= hidePointsTime && !Game.inst().arePointsHidden()) Game.inst().hidePoints();
        if(time <= activatePvPTime && !Game.inst().getPvpHandler().isPvpActive()) Game.inst().getPvpHandler().allowPvP();
    }

    public String getGameTimeToString(){
        return getTimeToString(gameDuration);
    }
    public String getHideTimeToString(){
        return getTimeToString(hidePointsTime);
    }
    public String getPvPTimeToString(){
        return getTimeToString(activatePvPTime);
    }

    private String getTimeToString(int time){
        return time >= 3600 ? time/3600 + "h " + (time/60)%60 + "m" : time/60 + "m " + time%60 + "s";
    }

    private void updateBarTime(){
        timeBar.setTitle(String.format(Texts.TIME_BAR, getTimeToString(time)));
        timeBar.setProgress(time / (double) gameDuration);
    }
}
