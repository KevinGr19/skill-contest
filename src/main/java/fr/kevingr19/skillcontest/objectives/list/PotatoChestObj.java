package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ChestClosedEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PotatoChestObj extends Objective {

    public PotatoChestObj(GameTeam gameTeam) {
        super(ObjectiveType.POTATO_CHEST, Rarity.IMPOSSIBLE, "Rations de guerre",
                "Remplir un coffre de patates.",
                "L'objectif s'accomplit à la fermeture\nd'un coffre remplit de patates.\n" +
                        "Ne laissez donc pas vos ennemis\nvoler votre coffre.", new ItemStack(Material.POTATO),
                true, gameTeam);
    }

    @EventHandler
    public void onChestClosed(final ChestClosedEvent e){
        if(!isInTeam(e.getPlayer())) return;

        Inventory inv = e.getInventory();
        if(inv.contains(Material.POTATO, inv.getSize() * 64)) complete(e.getPlayer());
    }
}
