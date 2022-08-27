package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ChestClosedEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SandChestObj extends Objective {

    public SandChestObj(GameTeam gameTeam) {
        super(ObjectiveType.SAND_CHEST, Rarity.DIFFICULT, "Archéologue en pâté en croûte",
                "Remplir un coffre de sable.",
                "L'objectif s'accomplit à la fermeture\nd'un coffre remplit de sable.\n" +
                        "Ne laissez donc pas vos ennemis\nvoler votre coffre.", new ItemStack(Material.SAND),
                true, gameTeam);
    }

    @EventHandler
    public void onChestClosed(final ChestClosedEvent e){
        if(!isInTeam(e.getPlayer())) return;

        Inventory inv = e.getInventory();
        if(inv.contains(Material.SAND, inv.getSize() * 64)) complete(e.getPlayer());
    }
}
