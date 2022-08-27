package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.gui.inventory.TeamSelectGUI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Player of a {@link Game}.
 * Holds methods for changing {@link GameTeam}.
 */

public class GamePlayer {

    private String name;
    private final UUID uuid;
    private GameTeam team;

    public GamePlayer(String name, UUID uuid){
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof GamePlayer player)) return false;
        return uuid.equals(player.uuid);
    }

    @Nullable
    public GameTeam getGameTeam() {
        return team;
    }
    public boolean hasGameTeam(){
        return team != null;
    }

    public void setGameTeam(GameTeam team, boolean update){
        if(this.team == team) return;

        if(hasGameTeam()) {
            this.team.removeMember(this);
            if(update) TeamSelectGUI.updateSingleButton(this.team.color());
        }
        if(team != null) {
            team.addMember(this);
            if(update) TeamSelectGUI.updateSingleButton(team.color());
        }
        this.team = team;
        updateName();
    }

    public void setGameTeam(GameTeam team){
        setGameTeam(team, true);
    }

    public void sendTeamChangeMessage(Player player){
        if(player == null || !player.getUniqueId().equals(uuid)) return;
        if(team == null) player.sendMessage(Texts.PLUGIN_MSG + "§fVous avez été retiré de votre équipe.");
        else player.sendMessage(Texts.PLUGIN_MSG + "§fVous avez rejoint " + team.team().getDisplayName() + "§f.");
    }

    public void updateName(){
        Player player = Bukkit.getPlayer(uuid);
        if(player == null || !player.isOnline()) return;

        if(hasGameTeam()) {
            player.setDisplayName(team.color().colorPrefix + player.getName());
            player.setPlayerListName(team.color().prefix + player.getName());
        }
        else{
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
        }
    }

    private boolean spawned = false;

    public boolean hasSpawned() {
        return spawned;
    }

    public void spawnPlayer(Player player, boolean tp){
        if(spawned || player == null || !player.getUniqueId().equals(uuid)) return;

        player.getInventory().clear();
        player.getEnderChest().clear();

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setLevel(0);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.setBedSpawnLocation(null);
        if(tp) player.teleport(Game.inst().getBaseWorld().getSpawnLocation());
        player.setGameMode(GameMode.SURVIVAL);

        spawned = true;
    }
}
