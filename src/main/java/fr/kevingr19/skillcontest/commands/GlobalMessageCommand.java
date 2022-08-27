package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.constants.Texts;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GlobalMessageCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            String message = String.join(" ", args);
            Bukkit.broadcastMessage("<" + player.getDisplayName() + "§f> " + message);
        }
        else sender.sendMessage(Texts.PLAYER_COMMAND);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
