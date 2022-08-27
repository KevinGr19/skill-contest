package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor of /skc init.
 * Sets the game to a wait state, and sets the host to the command sender.
 */

public class InitiateGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){

            if(!player.isOp() && !Game.inst().isHost(player))
                player.sendMessage(Texts.OP_COMMAND);
            else if(!Game.inst().isState(Game.State.NONE))
                player.sendMessage(Texts.ERROR + "Le jeu est déjà initialisé.");
            else
                Game.inst().initGame(player);

        }
        else Main.error(Texts.PLAYER_COMMAND);
        return true;
    }
}
