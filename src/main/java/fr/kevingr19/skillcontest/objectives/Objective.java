package fr.kevingr19.skillcontest.objectives;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Sounds;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.utils.Broadcast;
import fr.kevingr19.skillcontest.utils.SoundComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Base class of all objectives.
 */

abstract public class Objective implements Listener {

    public enum Rarity{
        EASY("Facile", "§a", Texts.OBJ_DONE, Sounds.EASY_OBJ),
        MODERATE("Moyen", "§3", Texts.OBJ_DONE, Sounds.MODERATE_OBJ),
        DIFFICULT("Difficile", "§c", Texts.OBJ_DONE, Sounds.DIFFICULT_OBJ),
        IMPOSSIBLE("Impossible", "§6", Texts.IMPOSSIBLE_OBJ_DONE, Sounds.IMPOSSIBLE_OBJ),
        LEGENDARY("Légendaire", "§5", Texts.LEGENDARY_OBJ_DONE, Sounds.LEGENDARY_OBJ);

        public final String displayName;
        public final String color;
        public final String formatMsg;

        public final SoundComponent soundComponent;

        Rarity(String displayName, String color, String formatMsg, SoundComponent soundComponent){
            this.displayName = displayName;
            this.color = color;
            this.formatMsg = formatMsg;
            this.soundComponent = soundComponent;
        }

    }

    public final ObjectiveType type;

    public final String name;
    public final String titleName;
    public final String buttonName;

    public final String header;
    public final String description;
    public final String notabene;

    public final ItemStack logo;
    public final int points;
    public final Rarity rarity;

    public final GameTeam gameTeam;
    public final boolean common;

    protected boolean done = false;
    protected String solverName = null;

    public Objective(ObjectiveType type, Rarity rarity, String name, String description, String notabene, ItemStack logo, boolean common, GameTeam gameTeam){
        this.type = type;
        this.rarity = rarity;

        this.name = name;
        this.titleName = rarity.color + rarity.displayName + " : " + name;
        this.buttonName = rarity.color + "[" + name + "]";

        this.header = "§fPoints : §e" + type.points;
        this.description = description;
        this.notabene = notabene == null ? null : "NOTE: " + notabene;

        this.logo = logo;
        this.points = type.points;
        this.common = common;

        this.gameTeam = gameTeam;
    }

    public boolean isDone(){
        return done;
    }
    public String getSolverDisplayName(){
        return gameTeam.team().getColor() + solverName;
    }

    public String getLore(UUID uuid){
        return "§7" + description.replaceAll("\\R", "\n§7") + "\n\n" + header + "\n" + getProgression(uuid) +
                (notabene == null ? "" : "\n\n§7" + notabene.replaceAll("\\R", "\n§7"));
    }

    protected String getProgression(UUID uuid){
        return isDone() ? "§fFait : §a" + solverName : "§fFait : §cNon";
    }

    protected boolean isInTeam(OfflinePlayer player){
        return player != null && gameTeam.isMember(player);
    }

    final public void complete(OfflinePlayer player){
        if(done) return;

        done = true;
        solverName = player.getName();

        gameTeam.addPoints(points);
        stop();

        if(Game.inst().arePointsHidden()) Broadcast.broadcastObjective(this);
        else Broadcast.broadcastObjective(this, points);
    }

    public void start(){
        Bukkit.getPluginManager().registerEvents(this, Main.inst());
    }

    public void stop(){
        HandlerList.unregisterAll(this);
    }
}
