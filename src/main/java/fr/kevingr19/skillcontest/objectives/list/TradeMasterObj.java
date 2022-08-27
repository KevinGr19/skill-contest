package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.VillagerTradeEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class TradeMasterObj extends Objective {

    public TradeMasterObj(GameTeam gameTeam) {
        super(ObjectiveType.TRADE_MASTER, Rarity.DIFFICULT, "Investisseur",
                "Échanger avec un villageois Maître.", null,
                new ItemStack(Material.VILLAGER_SPAWN_EGG, 2), false, gameTeam);
    }

    @EventHandler
    public void onTrade(final VillagerTradeEvent e){
        if(isInTeam(e.getPlayer()) && e.getVillager().getVillagerLevel() == 5) complete(e.getPlayer());
    }
}
