package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import fr.kevingr19.skillcontest.objectives.TravelData;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SwimTravelObj extends TeamObjective<Double>{

    private final Map<UUID, Integer> lastTravelStat = new HashMap<>();

    public SwimTravelObj(GameTeam gameTeam) {
        super(ObjectiveType.SWIM_TRAVEL, Rarity.MODERATE, "Nageur infatigable",
                "Parcourir 1500 blocs en nageant.",
                "Le compteur se mettra à jour lorsque\nvous arrêterez de nager.",
                new ItemStack(Material.TURTLE_HELMET), gameTeam);
    }

    @Override
    protected ObjectiveData<Double> createObjectiveData() {
        return new TravelData(this, false, 1500);
    }

    @EventHandler
    public void onSwimToggle(EntityToggleSwimEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player)) return;
        if(!player.isSwimming())
            lastTravelStat.put(player.getUniqueId(), player.getStatistic(Statistic.SWIM_ONE_CM));
        else{
            double progress = (player.getStatistic(Statistic.SWIM_ONE_CM) - lastTravelStat.get(player.getUniqueId())) / 100D;
            objectiveData.addProgress(player, progress);
        }
    }
}
