package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInventoryNewItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class DeepslateStacksObj extends Objective {

    private static final int itemAmount = 64*12;

    public DeepslateStacksObj(GameTeam gameTeam) {
        super(ObjectiveType.DEEPSLATE_STACKS, Rarity.MODERATE, "Toujours pas de fond...",
                "Avoir 12 stacks de Pierre des Âbimes.", null,
                new ItemStack(Material.COBBLED_DEEPSLATE), false, gameTeam);
    }

    @EventHandler
    public void onDeepslateAcquire(final PlayerInventoryNewItemEvent e){
        if(e.getMaterial() != Material.COBBLED_DEEPSLATE || !isInTeam(e.getPlayer())) return;
        if(e.getPlayer().getInventory().contains(Material.COBBLED_DEEPSLATE, itemAmount)) complete(e.getPlayer());
    }
}
