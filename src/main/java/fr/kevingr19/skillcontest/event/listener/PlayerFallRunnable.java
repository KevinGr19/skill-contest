package fr.kevingr19.skillcontest.event.listener;

import fr.kevingr19.skillcontest.event.PlayerLandEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * {@link BukkitRunnable} used to track the fall distance of a player up until it resets,
 * at which point a {@link PlayerLandEvent} will be called.
 */

public class PlayerFallRunnable extends BukkitRunnable {

    private final Player player;
    private float lastFallDistance;

    public  PlayerFallRunnable(Player player){
        this.player = player;
    }

    @Override
    public void run(){
        if(!player.isOnline()){
            cancel();
            return;
        }

        if(player.isDead()){
            lastFallDistance = 0;
            return;
        }

        else if(lastFallDistance > 0 && player.getFallDistance() <= 0 && player.getGameMode() != GameMode.CREATIVE){
            PlayerLandEvent event = new PlayerLandEvent(player, lastFallDistance);
            Bukkit.getPluginManager().callEvent(event);
        }

        lastFallDistance = player.getFallDistance();

    }
}
