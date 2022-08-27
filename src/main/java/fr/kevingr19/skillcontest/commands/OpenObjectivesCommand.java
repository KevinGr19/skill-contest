package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GamePlayer;
import fr.kevingr19.skillcontest.gui.inventory.ObjectiveGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenObjectivesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){

            if(!Game.inst().isStates(Game.State.RUN, Game.State.END))
                player.sendMessage(Texts.ERROR + "Le jeu n'a pas encore commencé.");

            else if(args.length < 2)
            {
                if(!Game.inst().isPlaying(player, true))
                    player.sendMessage(Texts.ERROR + "Vous n'êtes pas un joueur de la partie.");
                else{
                    GamePlayer gamePlayer = Game.inst().getGamePlayer(player);
                    ObjectiveGUI.openPlayerInventory(gamePlayer, player);
                }
            }

            else if(!player.getName().equals(args[1]) && !Game.inst().isState(Game.State.END))
                player.sendMessage(Texts.ERROR + "Vous pourrez voir les objectifs d'autres joueurs à la fin de la partie.");

            else{
                if(!Game.inst().isPlaying(args[1], true))
                    player.sendMessage(Texts.ERROR + "Ce joueur n'est pas un joueur de la partie.");
                else{
                    GamePlayer gamePlayer = Game.inst().getGamePlayer(args[1]);
                    ObjectiveGUI.openPlayerInventory(gamePlayer, player);
                }
            }

        }
        else Main.error(Texts.PLAYER_COMMAND);
        return true;

    }
}
