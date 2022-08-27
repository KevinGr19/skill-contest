package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.gui.inventory.BookCollectionGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenBooksCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player)
        {
            if(!Game.inst().hasStarted())
                player.sendMessage(Texts.ERROR + "Le jeu n'a pas encore commencé.");
            else
                BookCollectionGUI.openPlayerInventory(player);
        }
        else sender.sendMessage(Texts.PLAYER_COMMAND);
        return true;
    }
}
