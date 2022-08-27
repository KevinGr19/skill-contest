package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInventoryNewItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class EmeraldsObj extends Objective {

    private static final int itemAmount = 64*3;

    public EmeraldsObj(GameTeam gameTeam) {
        super(ObjectiveType.EMERALDS, Rarity.IMPOSSIBLE, "Pété de thune",
                "Avoir 3 stacks d'émeraudes.", null,
                new ItemStack(Material.EMERALD), false, gameTeam);
    }

    @EventHandler
    public void onEmeraldAcquire(final PlayerInventoryNewItemEvent e){
        if(e.getMaterial() != Material.EMERALD || !isInTeam(e.getPlayer())) return;
        if(e.getPlayer().getInventory().contains(Material.EMERALD, itemAmount)) complete(e.getPlayer());
    }
}
