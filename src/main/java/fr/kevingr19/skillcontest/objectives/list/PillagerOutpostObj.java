package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.StructureManager;
import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class PillagerOutpostObj extends Objective {

    public PillagerOutpostObj(GameTeam gameTeam) {
        super(ObjectiveType.PILLAGER_OUTPOST, Rarity.EASY, "Los Pollos Hermanos",
                "Entrer dans un Avant-poste de pillards.", null,
                new ItemStack(Material.CROSSBOW), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInOutpost(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && StructureManager.isPillagerOutpost(e.getStructure(), e.getPlayer().getWorld())) complete(e.getPlayer());
    }
}
