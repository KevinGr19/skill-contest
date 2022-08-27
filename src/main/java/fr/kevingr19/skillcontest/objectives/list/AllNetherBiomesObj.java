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

public class AllNetherBiomesObj extends TeamObjective<String> {

    public AllNetherBiomesObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_NETHER_BIOMES, Rarity.DIFFICULT, "Voyage d'Enfer",
                "Visiter chaque biome du Nether.", null,
                new ItemStack(Material.LAVA_BUCKET), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Nether Wastes", "Crimson Forest",
                "Warped Forest", "Soul Sand Valley", "Basalt Deltas");
    }

    @EventHandler
    public void onBiomeChange(final PlayerChangeBiomeEvent e){
        if(!isInTeam(e.getPlayer())) return;
        objectiveData.addProgress(e.getPlayer(), e.getBiomeName());
    }
}