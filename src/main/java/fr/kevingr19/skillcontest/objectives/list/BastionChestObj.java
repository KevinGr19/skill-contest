package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.StructureManager;
import fr.kevingr19.skillcontest.event.ChestClosedEvent;
import fr.kevingr19.skillcontest.event.DoubleChestClosedEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BastionChestObj extends Objective {

    public BastionChestObj(GameTeam gameTeam) {
        super(ObjectiveType.BASTION_CHEST, Rarity.EASY, "Justin Bridou",
                "Ouvrir ou casser un coffre de bastion.", "Le coffre doit avoir été\ngénéré naturellement.",
                new ItemStack(Material.CHEST), false, gameTeam);
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
        Structure structure = Main.structureManager().getChestStructureOrigin(loc);
        if(StructureManager.isBastion(structure, player.getWorld())) complete(player);
    }
}
