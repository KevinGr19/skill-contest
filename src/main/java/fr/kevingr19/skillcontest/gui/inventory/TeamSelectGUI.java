package fr.kevingr19.skillcontest.gui.inventory;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GamePlayer;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.utils.InventoryUtil;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final public class TeamSelectGUI extends ClickGUI{

    private static TeamSelectGUI gui;

    public static void openForPlayer(Player player){
        if(!Game.inst().isState(Game.State.WAIT)) return;
        if(gui == null){
            gui = new TeamSelectGUI();
            updateButtons();
        }
        gui.openInventory(player);
    }

    @Override
    protected Inventory createInventory(String title) {
        return Bukkit.createInventory(null, 36, "Changer d'équipe");
    }

    public TeamSelectGUI(){
        super();
        activeActionMap = new HashMap<>();
        InventoryUtil.fillOutline(inv, ItemUtil.create(Material.BLACK_STAINED_GLASS_PANE, 1, " "));

        inv.setItem(35, ItemUtil.create(Material.BARRIER, 1, "§cQuitter l'équipe"));
        activeActionMap.put(35, e -> playerLeaveTeam((Player) e.getWhoClicked()));

        for(GameTeam.Color color : GameTeam.Color.values())
        {
            inv.setItem(color.guiSlot, ItemUtil.create(color.banner, 1, color.displayName));
            activeActionMap.put(color.guiSlot, e -> playerJoinTeam(color, (Player) e.getWhoClicked()));
        }
    }

    private void playerJoinTeam(GameTeam.Color color, Player player){
        GameTeam gameTeam = Game.teams().getTeam(color);

        if(!Game.inst().isState(Game.State.WAIT) || !Game.teams().canPlayerChoose() && !Game.inst().isHost(player))
            player.sendMessage(Texts.ERROR + "Vous n'avez actuellement pas le droit de choisir votre équipe.");

        else if(!gameTeam.isActive())
            player.sendMessage(Texts.ERROR + "Cette équipe est désactivée.");

        else if(!Game.teams().noPlayerLimit() && gameTeam.getMemberCount() >= Game.teams().getPlayerLimit())
            player.sendMessage(Texts.ERROR + "Cette équipe est complète.");

        else if(!Game.inst().isPlaying(player, false)) {
            Game.inst().addGamePlayer(player, Game.teams().getTeam(color));
            updateSingleButton(color);
        }

        else{
            GamePlayer gamePlayer = Game.inst().getGamePlayer(player);
            GameTeam oldTeam = gamePlayer.getGameTeam();
            if(oldTeam == gameTeam)
                player.sendMessage(Texts.ERROR + "Vous êtes déjà dans cette équipe.");

            else {
                gamePlayer.setGameTeam(gameTeam);
                gamePlayer.sendTeamChangeMessage(player);
            }
        }

        new BukkitRunnable() {@Override public void run(){player.closeInventory();}}.runTaskLater(Main.inst(), 0);
    }

    private void playerLeaveTeam(Player player){
        if(!Game.inst().isState(Game.State.WAIT) || !Game.teams().canPlayerChoose() && !Game.inst().isHost(player))
            player.sendMessage(Texts.ERROR + "Vous n'avez actuellement pas le droit de choisir votre équipe.");

        else if(!Game.inst().isPlaying(player, true))
            player.sendMessage(Texts.ERROR + "Vous n'êtes dans aucune équipe.");

        else {
            GamePlayer gamePlayer = Game.inst().getGamePlayer(player);

            gamePlayer.setGameTeam(null);
            gamePlayer.sendTeamChangeMessage(player);
        }

        new BukkitRunnable(){@Override public void run(){player.closeInventory();}}.runTaskLater(Main.inst(), 0);
    }

    public static void updateButtons() {
        if(gui != null) for(GameTeam.Color color : GameTeam.Color.values()) updateSingleButton(color);
    }

    public static void updateSingleButton(GameTeam.Color color){
        if(gui == null) return;
        ItemStack logo = gui.inv.getItem(color.guiSlot);

        GameTeam gameTeam = Game.teams().getTeam(color);
        List<String> lore = new ArrayList<>();

        if(!gameTeam.isActive()) {
            lore.add("§4Désactivée");
            logo.setType(Material.BARRIER);
        }
        else{
            if(Game.teams().noPlayerLimit()) lore.add("§fJoueurs : §a" + gameTeam.getMemberCount());
            else if(gameTeam.getMemberCount() >= Game.teams().getPlayerLimit()) lore.add("§4Plus de place");
            else lore.add("§fJoueurs : §a" + gameTeam.getMemberCount() + "/" + Game.teams().getPlayerLimit());

            for(GamePlayer gamePlayer : gameTeam.getMembers()) lore.add("§7- " + gamePlayer.getName());
            logo.setType(color.banner);
        }

       ItemUtil.setLore(logo, lore);
    }
}
