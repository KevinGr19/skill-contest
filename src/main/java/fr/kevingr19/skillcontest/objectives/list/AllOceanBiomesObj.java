package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerChangeBiomeEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AllOceanBiomesObj extends TeamObjective<String> {

    public AllOceanBiomesObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_OCEAN_BIOMES, Rarity.DIFFICULT, "Par-delà les 7 mers",
                "Visiter chaque biome aquatique.", null,
                new ItemStack(Material.MANGROVE_BOAT), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Frozen Ocean", "Deep Frozen Ocean", "Cold Ocean",
                "Deep Cold Ocean", "Ocean", "Deep Ocean", "Lukewarm Ocean", "Deep Lukewarm Ocean", "Warm Ocean",
                "River", "Frozen River");
    }

    @EventHandler
    public void onBiomeChange(final PlayerChangeBiomeEvent e){
        if(!isInTeam(e.getPlayer())) return;
        objectiveData.addProgress(e.getPlayer(), e.getBiomeName());
    }
}