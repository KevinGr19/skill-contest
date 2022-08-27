package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.PlayerObjective;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class RottenFleshObj extends PlayerObjective<Integer> {

    public RottenFleshObj(GameTeam gameTeam) {
        super(ObjectiveType.ROTTEN_EATER, Rarity.MODERATE, "Mangeur de zombie",
                "Manger 20 chairs putréfiées à la suite.", "Si une autre nouritture est consomée, alors\n" +
                        "la progression repartira à 0.", new ItemStack(Material.ROTTEN_FLESH), gameTeam);
    }

    @Override
    protected void addObjectiveData(UUID uuid) {
        objectiveDataMap.put(uuid, new CounterData(this, true, 20));
    }

    @EventHandler
    public void onConsume(final PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        if(!isInTeam(player)) return;

        if(e.getItem().getType() == Material.ROTTEN_FLESH) getObjectiveData(player.getUniqueId()).addProgress(player, 1);
        else if(e.getItem().getType().isEdible()) getObjectiveData(player.getUniqueId()).resetProgress();
    }
}
