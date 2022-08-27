package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GamePlayer;
import fr.kevingr19.skillcontest.game.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){
            if(!Game.inst().isState(Game.State.WAIT))
                player.sendMessage(Texts.ERROR + "Vous ne pouvez actuellement pas gérer les équipes.");
            else if(!Game.inst().isHost(player))
                player.sendMessage(Texts.HOST_COMMAND);

            else
            {
                if(args.length < 2 || args[0].equalsIgnoreCase("help")) sendHelpMessage(sender);
                else if(args[1].equalsIgnoreCase("set")) setPlayerTeam(player, args);
                else if(args[1].equalsIgnoreCase("remove")) removePlayerTeam(player, args);
                else player.sendMessage(String.format(Texts.INVALID_ARG, args[1]));
            }
        }
        else Main.error(Texts.PLAYER_COMMAND);

        return true;
    }

    private void sendHelpMessage(CommandSender sender){
        sender.sendMessage(Texts.CHAT_BAR);
        sender.sendMessage(String.format(Texts.HELP_TITLE, "§6/skc teams"));
        sender.sendMessage(String.format(Texts.HELP_BRANCH, "/skc teams §eset <joueur> <équipe>", "Change l'équipe d'un joueur."));
        sender.sendMessage(String.format(Texts.HELP_BRANCH, "/skc teams §eremove <joueur>", "Retire un joueur d'une équipe."));
        sender.sendMessage(Texts.CHAT_BAR);
    }

    private void setPlayerTeam(Player sender, String[] args){
        if(args.length < 3) {
            sender.sendMessage(Texts.ERROR + "Veuillez spécifier un nom de joueur.");
            return;
        }
        if(args.length < 4) {
            sender.sendMessage(Texts.ERROR + "Veuillez spécifier un nom d'équipe.");
            return;
        }

        GameTeam.Color color = GameTeam.Color.bySimpleName(args[3]);
        if(color == null){
            sender.sendMessage(Texts.ERROR + "Cette équipe n'existe pas.");
            return;
        }
        GameTeam team = Game.teams().getTeam(color);

        final String playerName = args[2];

        Player player = Bukkit.getPlayer(playerName);
        GamePlayer gamePlayer = Game.inst().getGamePlayer(playerName);

        if(gamePlayer == null)
        {
            if(player == null || !player.isOnline()) sender.sendMessage(Texts.ERROR + "Ce joueur n'existe pas.");
            else if(joinTeamChecks(sender, team, null)){
                Game.inst().addGamePlayer(player, team);
                sender.sendMessage(Texts.PLUGIN_MSG + "§r§n" + playerName + "§r a bien été inscrit dans la partie dans " + color.displayName);
            }
        }
        else if(joinTeamChecks(sender, team, gamePlayer))
        {
            gamePlayer.setGameTeam(team);
            gamePlayer.sendTeamChangeMessage(player);
            sender.sendMessage(Texts.PLUGIN_MSG + "§rL'équipe de §n" + playerName + "§r a bien été changée à " + color.displayName);
        }

    }

    private boolean joinTeamChecks(Player sender, GameTeam team, GamePlayer gamePlayer){
        if(!team.isActive()) {
            sender.sendMessage(Texts.ERROR + "Cette équipe est désactivée.");
            return false;
        }
        else if(gamePlayer != null && gamePlayer.getGameTeam() == team){
            sender.sendMessage(Texts.ERROR + "Ce joueur est déjà dans cette équipe.");
            return false;
        }
        else if(!Game.teams().noPlayerLimit() && team.getMemberCount() >= Game.teams().getPlayerLimit()){
            sender.sendMessage(Texts.ERROR + "Cette équipe est complète.");
            return false;
        }
        return true;
    }

    private void removePlayerTeam(Player sender, String[] args){
        if(args.length < 3) {
            sender.sendMessage(Texts.ERROR + "Veuillez spécifier un nom de joueur.");
            return;
        }

        final String playerName = args[2];

        if(!Game.inst().isPlaying(playerName, true)) {
            sender.sendMessage(Texts.ERROR + "Ce joueur n'est pas inscrit.");
            return;
        }

        GamePlayer gamePlayer = Game.inst().getGamePlayer(playerName);
        gamePlayer.setGameTeam(null);
        gamePlayer.sendTeamChangeMessage(Bukkit.getPlayer(playerName));
        sender.sendMessage(Texts.PLUGIN_MSG + "§r§n" + playerName + "§r a bien été retiré de son équipe.");
    }
}
