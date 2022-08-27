package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.StructureManager;
import fr.kevingr19.skillcontest.event.ChestClosedEvent;
import fr.kevingr19.skillcontest.event.DoubleChestClosedEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CityPillageObj extends TeamObjective<Integer> {

    private final Set<Location> openedChests = new HashSet<>();

    public CityPillageObj(GameTeam gameTeam) {
        super(ObjectiveType.CITY_PILLAGE, Rarity.DIFFICULT, "Pillage furtif",
                "Ouvrir ou casser 8 coffres différents\nprovenant d'une Ancienne Cité.",
                "Les coffres doivent avoir été\ngénérés naturellement.", new ItemStack(Material.CHEST, 2), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 8);
    }

    @EventHandler
    public void onChestClosed(final ChestClosedEvent e){
        if(isInTeam(e.getPlayer())) playerTriggerChest(e.getPlayer(), e.getLocation());
    }

    @EventHandler
    public void onDoubleChestClosed(final DoubleChestClosedEvent e){
        if(isInTeam(e.getPlayer())){
            playerTriggerChest(e.getPlayer(), e.getLoc1());
            playerTriggerChest(e.getPlayer(), e.getLoc2());
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onChestBreak(final BlockBreakEvent e){
        if(!isInTeam(e.getPlayer())) return;
        if(e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST)
            playerTriggerChest(e.getPlayer(), e.getBlock().getLocation());
    }

    private void playerTriggerChest(final Player player, final Location loc){
        if(openedChests.contains(loc)) return;

        Structure structure = Main.structureManager().getChestStructureOrigin(loc);
        if(StructureManager.isAncientCity(structure, player.getWorld())){
            objectiveData.addProgress(player, 1);
            openedChests.add(loc);
        }
    }
}
