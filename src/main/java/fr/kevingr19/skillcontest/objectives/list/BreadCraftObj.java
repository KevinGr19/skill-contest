package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BreadCraftObj extends Objective {

    public BreadCraftObj(GameTeam gameTeam) {
        super(ObjectiveType.BREAD_CRAFT, Rarity.EASY, "1€80 s'il vous plaît",
                "Fabriquer du pain.",
                null, new ItemStack(Material.BREAD), false, gameTeam);
    }

    @EventHandler
    public void onBreadCraft(final ResultItemTakenEvent e){
        if(e.isCrafting() && isInTeam(e.getPlayer()) && e.getResult().getType() == Material.BREAD) complete(e.getPlayer());
    }
}
