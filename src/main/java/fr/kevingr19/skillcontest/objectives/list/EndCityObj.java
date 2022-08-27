package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class EndCityObj extends Objective {

    public EndCityObj(GameTeam gameTeam) {
        super(ObjectiveType.END_CITY, Rarity.EASY, "Ils sont perchés",
                "Entrer dans une Cité de l'End", null,
                new ItemStack(Material.PURPUR_PILLAR), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInCity(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof EndCityStructure) complete(e.getPlayer());
    }
}
