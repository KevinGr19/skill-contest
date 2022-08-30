package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.objectives.list.BigMapObj;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor of /skc image.
 * Changes the image of BigMapObj.
 */

public class ChangeImageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){

            if(!Game.inst().isHost(player))
                player.sendMessage(Texts.HOST_COMMAND);
            else if(Game.inst().hasStarted())
                player.sendMessage(Texts.ERROR + "La partie a déjà commencé.");
            else if(args.length < 2)
                player.sendMessage(Texts.ERROR + "Veuillez spécifier un url.");
            else{
                if(!BigMapObj.loadImage(args[1])) player.sendMessage(Texts.ERROR + "Une erreur est survenue lors de la commande. Vérifiez que l'URL est correct.");
                else player.sendMessage(Texts.PLUGIN_MSG + "§aImage d'objectif modifiée.");
            }

        }
        else Main.error(Texts.PLAYER_COMMAND);
        return true;
    }
}
