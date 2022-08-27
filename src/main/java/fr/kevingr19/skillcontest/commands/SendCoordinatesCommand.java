package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SendCoordinatesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){

            if(!Game.inst().hasStarted())
                player.sendMessage(Texts.ERROR + "Le jeu n'a pas encore commencé.");

            else if(!Game.inst().isPlaying(player, true))
                player.sendMessage(Texts.ERROR + "Vous n'avez pas d'équipe à qui communiquer vos coordonées.");

            else{
                String msg = String.format(Texts.COORDS_MSG, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                if(args.length > 1) msg += " §7§o(" + String.join(" ", Arrays.copyOfRange(args, 1, args.length)) + ")";

                Game.inst().getGamePlayer(player).getGameTeam().sendTeamMsg(player, msg);
            }

        }
        else Main.error(Texts.PLAYER_COMMAND);
        return true;

    }
}
