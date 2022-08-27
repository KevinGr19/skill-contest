package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHostCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            if(!Game.inst().isHost(player))
                player.sendMessage(Texts.HOST_COMMAND);
            else if(!Game.inst().isState(Game.State.WAIT))
                player.sendMessage(Texts.ERROR + "Vous ne pouvez actuellement pas choisir un host.");
            else if(args.length < 2)
                player.sendMessage(Texts.ERROR + "Veuillez spécifier un joueur.");
            else{
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null)
                    player.sendMessage(Texts.ERROR + "Ce joueur n'existe pas ou n'est pas connecté.");
                else if(Game.inst().isHost(target))
                    player.sendMessage(Texts.ERROR + "Vous êtes déjà le host de la partie.");
                else
                    Game.inst().setHost(target);
            }

        }
        else Main.error(Texts.PLAYER_COMMAND);
        return true;
    }
}
