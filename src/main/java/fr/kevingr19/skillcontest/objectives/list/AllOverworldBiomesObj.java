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

public class AllOverworldBiomesObj extends TeamObjective<String> {

    public AllOverworldBiomesObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_OVERWORLD_BIOMES, Rarity.LEGENDARY, "Troisième oeil",
                "Visiter chaque biome continental de l'Overworld.", null,
                new ItemStack(Material.CARTOGRAPHY_TABLE), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, 40, "Plains", "Sunflower Plains", "Snowy Plains",
                "Ice Spikes", "Desert", "Swamp", "Mangrove Swamp", "Forest", "Flower Forest", "Birch Forest", "Dark Forest", "Old Growth Birch Forest",
                "Old Growth Pine Taiga", "Old Growth Spruce Taiga", "Taiga", "Snowy Taiga", "Savanna", "Savanna Plateau", "Windswept Hills", "Windswept Gravelly Hills",
                "Windswept Forest", "Windswept Savanna", "Jungle", "Sparse Jungle", "Bamboo Jungle", "Badlands", "Eroded Badlands", "Wooded Badlands",
                "Meadow", "Grove", "Snowy Slopes", "Frozen Peaks", "Jagged Peaks", "Stony Peaks", "Beach", "Snowy Beach", "Stony Shore", "Mushroom Fields");
    }

    @EventHandler
    public void onBiomeChange(final PlayerChangeBiomeEvent e){
        if(!isInTeam(e.getPlayer())) return;
        objectiveData.addProgress(e.getPlayer(), e.getBiomeName());
    }
}