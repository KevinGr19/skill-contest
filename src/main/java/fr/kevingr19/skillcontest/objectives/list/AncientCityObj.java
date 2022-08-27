package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.StructureManager;
import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AncientCityObj extends Objective {

    public AncientCityObj(GameTeam gameTeam) {
        super(ObjectiveType.ANCIENT_CITY, Rarity.MODERATE, "Civilisation déchue",
                "Entrer dans une Ancienne Cité.", null,
                new ItemStack(Material.REINFORCED_DEEPSLATE), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInAncientCity(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && StructureManager.isAncientCity(e.getStructure(), e.getPlayer().getWorld())) complete(e.getPlayer());
    }
}
