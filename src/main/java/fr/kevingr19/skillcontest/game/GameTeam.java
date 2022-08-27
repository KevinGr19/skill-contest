package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class GameTeam {

    /**
     * All the colors available to make teams.
     * There is only one {@link GameTeam} associated with a {@link Color}.
     */

    public enum Color {
        RED(ChatColor.RED, "Rouge", Material.RED_BANNER, 10),
        ORANGE(ChatColor.GOLD, "Orange", Material.ORANGE_BANNER, 11),
        YELLOW(ChatColor.YELLOW, "Jaune", Material.YELLOW_BANNER, 20),
        GREEN(ChatColor.DARK_GREEN, "Vert", Material.GREEN_BANNER, 21),
        AQUA(ChatColor.AQUA, "Aqua", Material.LIGHT_BLUE_BANNER, 23),
        BLUE(ChatColor.BLUE, "Bleu", Material.BLUE_BANNER, 24),
        PINK(ChatColor.LIGHT_PURPLE, "Rose", Material.PINK_BANNER, 15),
        PURPLE(ChatColor.DARK_PURPLE, "Violet", Material.PURPLE_BANNER, 16);

        public final ChatColor colorPrefix;
        public final String simpleName;
        public final String displayName;
        public final String prefix;

        public final Material banner;
        public final int guiSlot;

        Color(ChatColor colorPrefix, String simpleName, Material banner, int slot){
            this.colorPrefix = colorPrefix;
            this.simpleName = simpleName;
            this.displayName = colorPrefix + "Équipe " + simpleName;
            this.prefix = colorPrefix + "[" + simpleName + "] ";

            this.banner = banner;
            this.guiSlot = slot;
        }

        @Nullable
        public static Color bySimpleName(String name){
            for(Color color : values()) if(color.simpleName.equalsIgnoreCase(name)) return color;
            return null;
        }
    }

    private final Color color;
    private final Team team;
    private final ItemStack logo;
    private boolean active = true;

    private boolean playing = false;
    private boolean winner = false;
    private final Map<ObjectiveType, Objective> objectives = new HashMap<>();

    private final Set<GamePlayer> members = new HashSet<>();
    private int points = 0;

    public GameTeam(Color color, @Nonnull Team team, @Nonnull ItemStack logo){
        this.color = color;
        this.team = team;
        this.logo = logo;
    }

    public Color color(){
        return color;
    }
    public Team team(){
        return team;
    }
    public ItemStack getLogo(){
        return logo;
    }

    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        if(this.active && !active) for(GamePlayer member : members) {
            member.setGameTeam(null, false);

            Player player = Bukkit.getPlayer(member.getUuid());
            if(player != null) member.sendTeamChangeMessage(player);
        }
        this.active = active;
    }

    public boolean isPlaying() {
        return playing;
    }
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    void addMember(GamePlayer gamePlayer){
        if(members.contains(gamePlayer)) return;
        members.add(gamePlayer);
        team.addEntry(gamePlayer.getName());
    }

    void removeMember(GamePlayer gamePlayer){
        if(!members.contains(gamePlayer)) return;
        members.remove(gamePlayer);
        team.removeEntry(gamePlayer.getName());
    }

    public int getMemberCount(){
        return members.size();
    }

    public boolean isMember(OfflinePlayer player){
        return isMember(player.getUniqueId());
    }

    public boolean isMember(UUID uuid){
        GamePlayer gamePlayer = Game.inst().getGamePlayer(uuid);
        return gamePlayer != null && members.contains(gamePlayer);
    }

    public List<GamePlayer> getMembers(){
        return new ArrayList<>(members);
    }

    public int getPoints(){
        return points;
    }
    public void addPoints(int points){
        if(!playing) return;

        this.points += points;
        if(!Game.inst().arePointsHidden()) Game.scoreboard().updateTeamScores();
    }

    public void startObjectives(){
        if(!active || objectives.size() != 0) return;

        try{
            for(ObjectiveType type : ObjectiveType.values()) {
                objectives.put(type, type.objClass.getConstructor(GameTeam.class).newInstance(this));
                objectives.get(type).start();
            }
        }
        catch (Exception ex){
            Bukkit.broadcastMessage(Texts.ERROR + "CRITICAL : Could not instantiate a team objective using reflection.");
            ex.printStackTrace();
        }
    }

    public void stopObjectives(){
        if(!active || objectives.size() == 0) return;
        for(Objective objective : objectives.values()) objective.stop();
    }

    public Objective getObjective(ObjectiveType type){
        return objectives.get(type);
    }

    public void setWinner(){
        winner = true;
    }
    public boolean hasWon(){
        return winner;
    }

    public void sendTeamMsg(Player sender, String msg){
        if(!isMember(sender)) return;

        members.forEach(gamePlayer -> {
            final Player player = Bukkit.getPlayer(gamePlayer.getUuid());
            if(player != null) player.sendMessage(String.format(Texts.TEAM_MSG, sender.getDisplayName(), msg));
        });
    }
}
