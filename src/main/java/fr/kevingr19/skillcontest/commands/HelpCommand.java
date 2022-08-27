package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Texts.CHAT_BAR);
        sender.sendMessage(String.format(Texts.HELP_TITLE, "§6/skc"));

        sender.sendMessage(String.format(Texts.HELP_BRANCH, "§e/gb <message>", "Envoie un message global."));
        sender.sendMessage(String.format(Texts.HELP_BRANCH, "§e/skc objectifs", "Ouvre le menu des objectifs."));
        sender.sendMessage(String.format(Texts.HELP_BRANCH, "§e/skc pos <message>", "Envoie vos coordonées à votre équipe."));
        sender.sendMessage(String.format(Texts.HELP_BRANCH, "§e/skc livres", "Ouvre la bibliothèque."));

        if(sender.isOp() || sender instanceof Player player && Game.inst().isHost(player)){
            sender.sendMessage(String.format(Texts.HELP_BRANCH, "§6/skc init", "Initialise la partie."));
            sender.sendMessage(String.format(Texts.HELP_BRANCH, "§6/skc settings", "Ouvre la fenêtre des paramètres. §c(Host)"));
            sender.sendMessage(String.format(Texts.HELP_BRANCH, "§6/skc teams", "Permet de gérer les équipes des joueurs. §c(Host)"));
            sender.sendMessage(String.format(Texts.HELP_BRANCH, "§6/skc set_host", "Change le host de la partie. §c(Host)"));
        }

        sender.sendMessage(Texts.CHAT_BAR);
        return true;
    }
}
