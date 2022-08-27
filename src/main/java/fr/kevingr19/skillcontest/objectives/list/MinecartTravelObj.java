package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import fr.kevingr19.skillcontest.objectives.TravelData;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinecartTravelObj extends TeamObjective<Double>{

    private final Map<UUID, Integer> lastTravelStat = new HashMap<>();

    public MinecartTravelObj(GameTeam gameTeam) {
        super(ObjectiveType.MINECART_TRAVEL, Rarity.MODERATE, "Pass Navigo",
                "Parcourir 3000 blocs en wagon.",
                "Le compteur se mettra à jour lorsque\nvous sortirez du wagon.", new ItemStack(Material.MINECART), gameTeam);
    }

    @Override
    protected ObjectiveData<Double> createObjectiveData() {
        return new TravelData(this, true, 3000);
    }

    @EventHandler
    public void onEnterMinecart(final VehicleEnterEvent e){
        if(!(e.getEntered() instanceof Player player) || !isInTeam(player)) return;
        if(e.getVehicle().getType() == EntityType.MINECART)
            lastTravelStat.put(player.getUniqueId(), player.getStatistic(Statistic.MINECART_ONE_CM));
    }

    @EventHandler
    public void onExitMinecart(final VehicleExitEvent e){
        if(!(e.getExited() instanceof Player player) || !isInTeam(player)) return;
        if(e.getVehicle().getType() == EntityType.MINECART){
            double progress = (player.getStatistic(Statistic.MINECART_ONE_CM) - lastTravelStat.get(player.getUniqueId())) / 100D;
            objectiveData.addProgress(player, progress);
        }
    }
}
