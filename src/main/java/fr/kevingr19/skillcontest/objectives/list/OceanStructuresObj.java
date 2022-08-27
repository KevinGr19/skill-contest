package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class OceanStructuresObj extends TeamObjective<String> {

    public OceanStructuresObj(GameTeam gameTeam) {
        super(ObjectiveType.OCEAN_STRUCTURES, Rarity.DIFFICULT, "20 milieux sous les mers",
                "Entrer dans chaque structure sous-marine.", null,
                new ItemStack(Material.PRISMARINE_BRICKS), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, true, "Épave", "Coffre au trésor", "Ruines", "Temple Aquatique");
    }

    @EventHandler
    public void onPlayerInsideStructure(final PlayerInsideStructureEvent e){
        if(!isInTeam(e.getPlayer())) return;

        if(e.getStructure() instanceof OceanMonumentStructure)
            objectiveData.addProgress(e.getPlayer(), "Temple Aquatique");

        else if(e.getStructure() instanceof ShipwreckStructure)
            objectiveData.addProgress(e.getPlayer(), "Épave");

        else if(e.getStructure() instanceof BuriedTreasureStructure)
            objectiveData.addProgress(e.getPlayer(), "Coffre au trésor");

        else if(e.getStructure() instanceof OceanRuinStructure)
            objectiveData.addProgress(e.getPlayer(), "Ruines");
    }
}
