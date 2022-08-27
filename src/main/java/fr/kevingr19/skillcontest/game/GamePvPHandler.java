package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class GamePvPHandler implements Listener {

    private boolean pvpActive = false;
    private boolean teamPvP = true;

    public boolean isTeamPvP() {
        return teamPvP;
    }
    public void setTeamPvP(boolean teamPvP){
        this.teamPvP = teamPvP;
    }

    public boolean isPvpActive() {
        return pvpActive;
    }
    public void allowPvP(){
        if(!Game.inst().isState(Game.State.RUN)) return;

        pvpActive = true;
        Bukkit.broadcastMessage(Texts.BROADCAST + "§cLe PvP est désormais actif.");
        Main.inst().pingRunnable.pvp = "§6On";
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player victim)) return;

        if(e.getDamager() instanceof Projectile projectile){
            ProjectileSource source = projectile.getShooter();
            if(source instanceof Player shooter) e.setCancelled(shouldBlockHit(shooter, victim));
        }
        else if(e.getDamager() instanceof Player attacker) e.setCancelled(shouldBlockHit(attacker, victim));
    }

    private boolean shouldBlockHit(Player attacker, Player victim){
        if(attacker.getUniqueId().equals(victim.getUniqueId())) return false;
        if(!pvpActive) return true;
        if(teamPvP) return false;

        if(!Game.inst().isPlaying(attacker, true) || !Game.inst().isPlaying(victim, true))
            return false; // Security precaution : In case some spectator changes their game-mode

        GamePlayer gameAttacker = Game.inst().getGamePlayer(attacker);
        return Game.inst().getGamePlayer(victim).getGameTeam() == gameAttacker.getGameTeam();
    }
}
