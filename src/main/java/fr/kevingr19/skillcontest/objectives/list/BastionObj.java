package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.StructureManager;
import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BastionObj extends Objective {

    public BastionObj(GameTeam gameTeam) {
        super(ObjectiveType.BASTION, Rarity.EASY, "Quartier Chaud",
                "Entrer dans un Bastion.", null,
                new ItemStack(Material.POLISHED_BLACKSTONE_BRICK_STAIRS), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInBastion(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && StructureManager.isBastion(e.getStructure(), e.getPlayer().getWorld())) complete(e.getPlayer());
    }
}
