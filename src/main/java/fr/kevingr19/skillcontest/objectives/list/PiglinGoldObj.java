package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PiglinGetGoldIngotEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class PiglinGoldObj extends TeamObjective<Integer> {

    public PiglinGoldObj(GameTeam gameTeam) {
        super(ObjectiveType.PIGLIN_GOLD, Rarity.EASY, "Cartel de cochons",
                "Donner 20 lingots d'or à des piglins.", null,
                new ItemStack(Material.GOLD_INGOT), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, false, 20);
    }

    @EventHandler
    public void onPiglinGetIngot(final PiglinGetGoldIngotEvent e){
        OfflinePlayer player = Bukkit.getOfflinePlayer(e.getThrower());
        if(isInTeam(player)) objectiveData.addProgress(player, 1);
    }
}
