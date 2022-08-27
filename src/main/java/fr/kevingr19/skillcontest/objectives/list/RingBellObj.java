package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;

public class RingBellObj extends Objective {

    public RingBellObj(GameTeam gameTeam) {
        super(ObjectiveType.BELL_RING, Rarity.EASY, "Gong",
                "Sonner une cloche.", null,
                new ItemStack(Material.BELL), false, gameTeam);
    }

    @EventHandler
    public void onBellRing(PlayerStatisticIncrementEvent e){
        if(isInTeam(e.getPlayer()) && e.getStatistic() == Statistic.BELL_RING) complete(e.getPlayer());
    }
}
