package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerArrowKillMobEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class ArrowMobKillObj extends TeamObjective<Integer> {

    public ArrowMobKillObj(GameTeam gameTeam) {
        super(ObjectiveType.ARROW_KILL, Rarity.MODERATE, "Dans ma ligne de mire",
                "Tuer 30 mobs en utilisant une flèche.",
                "Les joueurs ne sont pas inclus.",
                new ItemStack(Material.ARROW), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, false, 30);
    }

    @EventHandler
    public void onKill(final PlayerArrowKillMobEvent e){
        if(isInTeam(e.getPlayer()) && e.getEntity().getType() != EntityType.PLAYER)
            objectiveData.addProgress(e.getPlayer(), 1);
    }
}
