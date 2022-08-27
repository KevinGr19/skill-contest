package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.VillagerTradeEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class TradeObj extends Objective {

    public TradeObj(GameTeam gameTeam) {
        super(ObjectiveType.TRADE, Rarity.EASY, "Bons procédés",
                "Échanger avec un villageois.", null,
                new ItemStack(Material.VILLAGER_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onTrade(final VillagerTradeEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
