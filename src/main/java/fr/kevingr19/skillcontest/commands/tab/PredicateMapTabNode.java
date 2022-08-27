package fr.kevingr19.skillcontest.commands.tab;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * {@link MapTabNode} variant that can contain conditions for each possible end.
 */

public class PredicateMapTabNode extends TabNode implements TabNode.MappedTabEnd {

    protected final Map<String, TabNode> nodes = new HashMap<>();
    protected final Map<String, Predicate<Player>> conditions = new HashMap<>();

    public PredicateMapTabNode(String name, TabNode... nodes) {
        super(name);
        addNext(nodes);
    }

    @Override
    public List<String> getPossibleWords() {
        return nodes.keySet().stream().toList();
    }

    public List<String> getPossibleWords(Player player){
        return nodes.keySet().stream().filter(key -> !conditions.containsKey(key) || conditions.get(key).test(player)).toList();
    }

    @Override
    public TabNode getNext(String name) {
        return nodes.getOrDefault(name, null);
    }

    @Override
    public void addNext(TabNode node) {
        nodes.put(node.name, node);
    }

    @Override
    public void addNext(TabNode... nodes) {
        for(TabNode node : nodes) addNext(node);
    }

    public void addEmptyNext(String name){
        nodes.put(name, null);
    }

    public void addEmptyNext(String... names){
        for(String name : names) addEmptyNext(name);
    }

    public void setPredicate(Predicate<Player> predicate, String... keys){
        Arrays.stream(keys).forEach(key -> conditions.put(key, predicate));
    }
}
