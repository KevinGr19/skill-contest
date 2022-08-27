package fr.kevingr19.skillcontest.commands;

import fr.kevingr19.skillcontest.commands.tab.*;
import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.game.GameTeam;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command executor of the unique plugin command /skc.
 * Acts as a hub for all the sub-commands.
 */

public class MainCommand implements TabExecutor {

    private static final PredicateMapTabNode tabTreeRoot;
    private static final Map<String, CommandExecutor> commands;

    static{
        tabTreeRoot = new PredicateMapTabNode("root");
        tabTreeRoot.addEmptyNext("help", "init", "settings", "pos", "livres");

        // "teams" node
        ListTabNode teamListNode = new ListTabNode("teamList", null);
        for(GameTeam.Color color : GameTeam.Color.values()) teamListNode.addWord(color.simpleName);

        PlayerTabNode setTeamNode = new PlayerTabNode("set", teamListNode,
                offlinePlayer -> offlinePlayer.isOnline() || Game.inst().isPlaying(offlinePlayer.getUniqueId(), true));

        PlayerTabNode removeTeamNode = new PlayerTabNode("remove", null,
                offlinePlayer -> Game.inst().isPlaying(offlinePlayer.getUniqueId(), true));

        tabTreeRoot.addNext(new MapTabNode("teams", setTeamNode, removeTeamNode));

        // "set_host" node
        tabTreeRoot.addNext(new PlayerTabNode("set_host", null, offlinePlayer ->
                offlinePlayer.isOnline() && !Game.inst().isHost(offlinePlayer.getUniqueId())));

        // "objectifs" node
        tabTreeRoot.addNext(new PlayerTabNode("objectifs", null,
                offlinePlayer -> Game.inst().isPlaying(offlinePlayer.getUniqueId(), true)));

        // Set sub-commands predicates
        tabTreeRoot.setPredicate(player -> player.isOp() && Game.inst().isState(Game.State.NONE), "init");
        tabTreeRoot.setPredicate(player -> Game.inst().isHost(player) && !Game.inst().hasStarted(), "settings", "teams", "set_host");
        tabTreeRoot.setPredicate(player -> Game.inst().hasStarted() && Game.inst().isPlaying(player, true), "pos");
        tabTreeRoot.setPredicate(player -> Game.inst().hasStarted(), "objectifs", "livres");

        // Assign command executors
        commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("init", new InitiateGameCommand());
        commands.put("settings", new OpenSettingsCommand());
        commands.put("teams", new TeamsCommand());
        commands.put("set_host", new SetHostCommand());
        commands.put("objectifs", new OpenObjectivesCommand());
        commands.put("pos", new SendCoordinatesCommand());
        commands.put("livres", new OpenBooksCommand());
    }

    private TabNode currentTabNode;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player) || args.length < 1) return null; // Error precaution, length should always be of at least 1
        currentTabNode = tabTreeRoot;

        for (int i = 0; i < args.length - 1; i++) {
            String arg = args[i];

            if (currentTabNode instanceof TabNode.SingleTabEnd)
                currentTabNode = ((TabNode.SingleTabEnd) currentTabNode).getNext();

            else if (currentTabNode instanceof TabNode.MappedTabEnd)
                currentTabNode = ((TabNode.MappedTabEnd) currentTabNode).getNext(arg.toLowerCase());

            else return new ArrayList<>();
            if(currentTabNode == null) return new ArrayList<>();
        }

        if(currentTabNode == tabTreeRoot) return StringUtil.copyPartialMatches(args[args.length-1],
                ((PredicateMapTabNode) currentTabNode).getPossibleWords(player), new ArrayList<>());

        return StringUtil.copyPartialMatches(args[args.length-1], currentTabNode.getPossibleWords(), new ArrayList<>());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(args.length < 1) return commands.get("help").onCommand(sender, command, label, args);
        if(!commands.containsKey(args[0])){
            sender.sendMessage(Texts.ERROR + "Cette commande n'existe pas. Veuillez taper la commande d'aide : §n/skc help");
            return true;
        }

        return commands.get(args[0]).onCommand(sender, command, label, args);

    }

}
