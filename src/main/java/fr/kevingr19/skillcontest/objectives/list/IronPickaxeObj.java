package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInventoryNewItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class IronPickaxeObj extends Objective {

    public IronPickaxeObj(GameTeam gameTeam) {
        super(ObjectiveType.IRON_PICKAXE, Rarity.EASY, "Au chantier !",
                "Obtenir une pioche en fer.", null,
                new ItemStack(Material.IRON_PICKAXE), false, gameTeam);
    }

    @EventHandler
    public void onObtainItem(final PlayerInventoryNewItemEvent e){
        if(e.getMaterial() == Material.IRON_PICKAXE && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
