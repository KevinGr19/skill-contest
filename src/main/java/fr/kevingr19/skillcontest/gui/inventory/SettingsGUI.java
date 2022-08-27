package fr.kevingr19.skillcontest.gui.inventory;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.game.GameTeamManager;
import fr.kevingr19.skillcontest.utils.InventoryUtil;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Custom GUI extending {@link MultiClickGUI}.
 * It allows the owner to set up the game settings.
 */

final public class SettingsGUI extends MultiClickGUI{

    private static final Map<UUID, SettingsGUI> inventories = new HashMap<>();

    public static void openForPlayer(Player player, Panels panel){
        if(!inventories.containsKey(player.getUniqueId())) inventories.put(player.getUniqueId(), new SettingsGUI());
        inventories.get(player.getUniqueId()).openInventoryAtFrame(player, panel.name);
    }
    public static void openForPlayer(Player player){
        openForPlayer(player, Panels.MENU);
    }

    private TeamSettings teamSettings;
    
    public SettingsGUI(){
        super(Panels.MENU.name);
        createFrames();
    }

    private void createFrames(){
        createMainFrame();
        createTeamFrame();
        createTimeFrame();
    }

    private void createMainFrame(){
        Frame menu = new Frame(27, "Paramètres");
        frames.put(Panels.MENU.name, menu);
        InventoryUtil.fillOutline(menu.inv(), ItemUtil.create(Material.BLACK_STAINED_GLASS_PANE, 1, " "));

        menu.inv().setItem(11, ItemUtil.create(Material.RED_BANNER, 1, "§cÉquipes"));
        menu.inv().setItem(13, ItemUtil.create(Material.CLOCK, 1, "§eTemps de jeu"));
        menu.inv().setItem(15, ItemUtil.create(Material.TRIPWIRE_HOOK, 1, "§aCommencer"));

        menu.setAction(11, e -> openInventoryAtFrame((Player) e.getWhoClicked(), Panels.TEAM.name));
        menu.setAction(13, e -> openInventoryAtFrame((Player) e.getWhoClicked(), Panels.TIME.name));
        menu.setAction(15, e -> {
            Game.inst().runChecksToStart();
            new BukkitRunnable(){@Override public void run(){e.getWhoClicked().closeInventory();}}.runTaskLater(Main.inst(), 0);
        });
    }

    private void createTeamFrame(){
        Frame team = new Frame(54, "Équipes");
        frames.put(Panels.TEAM.name, team);
        InventoryUtil.fillOutline(team.inv(), ItemUtil.create(Material.BLACK_STAINED_GLASS_PANE, 1, " "));

        team.inv().setItem(38, ItemUtil.create(Material.ENDER_EYE, 1, "§6Choix des équipes"));
        team.setAction(38, e -> {
            if(e != null) teamSettings.playerChoose = !teamSettings.playerChoose;
            ItemUtil.setLore(team.inv().getItem(38), teamSettings.playerChoose ? "§bTous" : "§3Host");
        });

        team.inv().setItem(40, ItemUtil.create(Material.IRON_SWORD, 1, "§6Friendly Fire"));
        team.setAction(40, e -> {
            if(e != null) teamSettings.teamPvP = !teamSettings.teamPvP;
            ItemUtil.setLore(team.inv().getItem(40), teamSettings.teamPvP ? "§aOui" : "§cNon");
        });

        team.inv().setItem(42, ItemUtil.create(Material.PLAYER_HEAD, 1, "§6Limite de joueurs par équipe"));
        team.setAction(42, e -> {
            if(e != null){
                if(!e.isShiftClick()){
                    if(e.isRightClick() && teamSettings.playerLimit < GameTeamManager.MAX_PLAYER_LIMIT) teamSettings.playerLimit++;
                    else if(e.isLeftClick() && teamSettings.playerLimit > 0) teamSettings.playerLimit--;
                }
                else{
                    if(e.isRightClick()) teamSettings.playerLimit = GameTeamManager.MAX_PLAYER_LIMIT;
                    else if(e.isLeftClick()) teamSettings.playerLimit = 0;
                }
            }
            String limit = teamSettings.playerLimit == 0 ? "§3Illimité" : "§fMax : §b" + teamSettings.playerLimit;
            ItemUtil.setLore(team.inv().getItem(42), Arrays.asList(limit,"",
                    "§7- Click gauche : §b-1",
                    "§7- Click droit : §b+1", "",
                    "§7- Shift click gauche : §3Illimité",
                    "§7- Shift click droit : §3Max"));
        });

        for(GameTeam.Color color : GameTeam.Color.values()){
            team.inv().setItem(color.guiSlot, ItemUtil.create(Material.BARRIER, 1, color.displayName));
            team.setAction(color.guiSlot, e -> {
                if(e != null) teamSettings.activeTeams.put(color, !teamSettings.activeTeams.get(color));
                ItemStack logo = team.inv().getItem(color.guiSlot);

                if(teamSettings.activeTeams.get(color)){
                    logo.setType(color.banner);
                    ItemUtil.setLore(logo, "§2Activée");
                }
                else{
                    logo.setType(Material.BARRIER);
                    ItemUtil.setLore(logo, "§4Désactivée");
                }
            });
        }

        team.inv().setItem(45, ItemUtil.create(Material.GREEN_WOOL, 1, "§aSauvegarder les changements"));
        team.setAction(45, e -> {
            Player player = (Player) e.getWhoClicked();

            if(teamSettings.checkTeams()) {
                teamSettings.saveChanges();
                openInventoryAtFrame(player, Panels.MENU.name);
                player.sendMessage(Texts.PLUGIN_MSG + "§aLes changements ont été sauvegardés.");
            }
            else player.sendMessage(Texts.ERROR + "Il faut au minimum 2 équipes actives pour lancer la partie.");
        });

        team.inv().setItem(53, ItemUtil.create(Material.RED_WOOL, 1, "§cRetour au menu principal"));
        team.setAction(53, e -> openInventoryAtFrame((Player) e.getWhoClicked(), Panels.MENU.name));

        team.setLoadAction(player -> loadTeamFrame());
    }

    private void createTimeFrame(){
        Frame time = new Frame(27, "Temps de jeu");
        frames.put(Panels.TIME.name, time);
        InventoryUtil.fillOutline(time.inv(), ItemUtil.create(Material.BLACK_STAINED_GLASS_PANE, 1, " "));

        time.inv().setItem(11, ItemUtil.create(Material.CLOCK, 1, "§3Temps de jeu"));
        time.setAction(11, e -> {
            if(e != null){
                if (e.isLeftClick()) Game.timer().addGameDuration(-15);
                else if (e.isRightClick()) Game.timer().addGameDuration(15);

                time.getAction(13).invoke(null);
                time.getAction(15).invoke(null);
            }

            ItemUtil.setLore(time.inv().getItem(11), Arrays.asList("§b" + Game.timer().getGameTimeToString(), "",
                    "§7- Click gauche : §b-15m",
                    "§7- Click droit : §b+15m"));
        });

        time.inv().setItem(13, ItemUtil.create(Material.DIAMOND_SWORD, 1, "§3Activation du PvP"));
        time.setAction(13, e -> {
            if(e != null){
                if (!e.isShiftClick()){
                    if(e.isLeftClick()) Game.timer().addActivatePvPTime(-5);
                    else if(e.isRightClick()) Game.timer().addActivatePvPTime(5);
                }
                else{
                    if(e.isLeftClick()) Game.timer().setActivatePvPTime(0);
                    else if(e.isRightClick()) Game.timer().setActivatePvPTime(Game.timer().getGameDuration());
                }
            }

            String timeStr = Game.timer().getActivatePvPTime() == 0 ? "§cPas de PvP" : "§fÀ partir de : §b" + Game.timer().getPvPTimeToString();

            ItemUtil.setLore(time.inv().getItem(13), Arrays.asList(timeStr, "",
                    "§7- Click gauche : §b-5m",
                    "§7- Click droit : §b+5m", "",
                    "§7- Shift click gauche : §3Min",
                    "§7- Shift click droit : §3Max"));
        });

        time.inv().setItem(15, ItemUtil.create(Material.SPYGLASS, 1, "§3Brouillage des scores"));
        time.setAction(15, e -> {
            if(e != null){
                if (!e.isShiftClick()){
                    if(e.isLeftClick()) Game.timer().addHidePointsTime(-5);
                    else if(e.isRightClick()) Game.timer().addHidePointsTime(5);
                }
                else{
                    if(e.isLeftClick()) Game.timer().setHidePointsTime(0);
                    else if(e.isRightClick()) Game.timer().setHidePointsTime(Game.timer().getGameDuration());
                }
            }

            String timeStr = Game.timer().getHidePointsTime() == 0 ? "§bNon" : "§fÀ partir de : §b" + Game.timer().getHideTimeToString();

            ItemUtil.setLore(time.inv().getItem(15), Arrays.asList(timeStr, "",
                    "§7- Click gauche : §b-5m",
                    "§7- Click droit : §b+5m", "",
                    "§7- Shift click gauche : §3Min",
                    "§7- Shift click droit : §3Max"));
        });

        time.inv().setItem(26, ItemUtil.create(Material.RED_WOOL, 1, "§cRetour au menu principal"));
        time.setAction(26, e -> openInventoryAtFrame((Player) e.getWhoClicked(), Panels.MENU.name));

        time.setLoadAction(player -> {
            time.getAction(11).invoke(null);
            time.getAction(13).invoke(null);
            time.getAction(15).invoke(null);
        });
    }

    private void loadTeamFrame(){
        Frame team = frames.get(Panels.TEAM.name);
        teamSettings = new TeamSettings();

        team.getAction(38).invoke(null);
        team.getAction(40).invoke(null);
        team.getAction(42).invoke(null);
        for(GameTeam.Color color : GameTeam.Color.values()) team.getAction(color.guiSlot).invoke(null);
    }

    private static class TeamSettings{

        public final Map<GameTeam.Color, Boolean> activeTeams;
        public boolean playerChoose;
        public int playerLimit;

        public boolean teamPvP;

        TeamSettings(){
            activeTeams = new HashMap<>();
            for(GameTeam.Color color : GameTeam.Color.values())
                activeTeams.put(color, Game.teams().getTeam(color).isActive());

            playerLimit = Game.teams().getPlayerLimit();
            playerChoose = Game.teams().canPlayerChoose();

            teamPvP = Game.inst().getPvpHandler().isTeamPvP();
        }

        public boolean checkTeams(){
            int teamActives = activeTeams.size();
            for(boolean active : activeTeams.values()){
                if(active) continue;
                if(teamActives-- <= 2) return false;
            }
            return true;
        }
        
        public void saveChanges(){
            for(GameTeam.Color color : activeTeams.keySet())
                Game.teams().getTeam(color).setActive(activeTeams.get(color));

            Game.teams().setPlayerLimit(playerLimit);
            Game.teams().setPlayerChoice(playerChoose);
            Game.inst().getPvpHandler().setTeamPvP(teamPvP);
            TeamSelectGUI.updateButtons();
        }
    }

    public enum Panels{
        MENU("menu"),
        TEAM("team"),
        TIME("time");

        private String name;

        Panels(String name){
            this.name = name;
        }
    }
}
