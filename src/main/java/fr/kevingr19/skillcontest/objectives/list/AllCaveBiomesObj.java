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

public class AllCaveBiomesObj extends TeamObjective<String> {

    public AllCaveBiomesObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_CAVE_BIOMES, Rarity.MODERATE, "Grottes sans secret",
                "Visiter chaque biome de cave.", null,
                new ItemStack(Material.BIG_DRIPLEAF), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Dripstone Caves", "Lush Caves", "Deep Dark");
    }

    @EventHandler
    public void onBiomeChange(final PlayerChangeBiomeEvent e){
        if(!isInTeam(e.getPlayer())) return;
        objectiveData.addProgress(e.getPlayer(), e.getBiomeName());
    }
}