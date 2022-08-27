package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.gui.inventory.SettingsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

/**
 * Command executor of /skc settings.
 * Opens the game settings for the player under certain conditions.
 */

public class OpenSettingsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){

            if(Game.inst().isState(Game.State.NONE))
                player.sendMessage(Texts.ERROR + "Le jeu n'a pas encore été initialisé.");
            else if(!Game.inst().isHost(player))
                player.sendMessage(Texts.HOST_COMMAND);
            else if(!Game.inst().isState(Game.State.WAIT))
                player.sendMessage(Texts.ERROR + "Le jeu a déjà commencé.");
            else {
                SettingsGUI.openForPlayer(player);

                ArmorStand armorStand = player.getWorld().spawn(player.getLocation().add(0,5,0), ArmorStand.class);
                armorStand.setGravity(false);
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                armorStand.setCollidable(false);

                armorStand.setCustomName("§7---- §bClassement final §7----\n" +
                        "§7- §e§l1er §r: §eJaune (1245)\n" +
                        "§7- §f§l2eme§r: §bAqua (1100)\n" +
                        "§7- §6§l3eme§r: §2Vert (975)\n");
                armorStand.setCustomNameVisible(true);
            }
        }
        else Main.error(Texts.PLAYER_COMMAND);
        return true;
    }
}
