package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerUseGoatHornEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class GoatHornObj extends Objective {

    public GoatHornObj(GameTeam gameTeam) {
        super(ObjectiveType.GOAT_HORN, Rarity.EASY, "ARAAAA !!!",
                "Souffler dans une corne de chèvre.", null,
                new ItemStack(Material.GOAT_HORN), false, gameTeam);
    }

    @EventHandler
    public void onHornUse(final PlayerUseGoatHornEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
