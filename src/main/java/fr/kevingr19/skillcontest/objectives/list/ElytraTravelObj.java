package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElytraTravelObj extends Objective {

    private final Map<UUID, Integer> lastTravelStat = new HashMap<>();

    public ElytraTravelObj(GameTeam gameTeam) {
        super(ObjectiveType.ELYTRA_TRAVEL, Rarity.DIFFICULT, "Consommateur de Redbull",
                "Parcourir 3000 blocs en Élytra\nen une seule fois, sans interruption.",
                null,
                new ItemStack(Material.PHANTOM_MEMBRANE), false, gameTeam);
    }

    @EventHandler
    public void onElytraToggle(final EntityToggleGlideEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player)) return;

        if(e.isGliding())
            lastTravelStat.put(player.getUniqueId(), player.getStatistic(Statistic.AVIATE_ONE_CM));
        else{
            int distance = (player.getStatistic(Statistic.AVIATE_ONE_CM) - lastTravelStat.get(player.getUniqueId()));
            Main.print("" + distance);
            if(distance >= 300000) complete(player);
        }
    }
}
