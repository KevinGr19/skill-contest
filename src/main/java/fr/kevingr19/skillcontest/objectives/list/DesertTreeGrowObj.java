package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class DesertTreeGrowObj extends Objective {

    private static final Set<Material> saplings = new HashSet<>();

    public DesertTreeGrowObj(GameTeam gameTeam) {
        super(ObjectiveType.DESERT_TREE_GROW, Rarity.EASY, "Un pas vers Mars",
                "Faire pousser un arbre dans un desert\nen utilisant de la poudre d'os.",
                null, new ItemStack(Material.BONE_MEAL), false, gameTeam);
    }

    @EventHandler
    public void onTreeGrow(final StructureGrowEvent e){
        if(!e.isFromBonemeal() || e.getLocation().getBlock().getBiome() != Biome.DESERT || !isInTeam(e.getPlayer())) return;
        final Material type = e.getLocation().getBlock().getType();

        // Check if sapling has grown
        new BukkitRunnable(){
            @Override public void run(){
                final Material newType = e.getLocation().getBlock().getType();
                if(newType != type && newType.isSolid()) complete(e.getPlayer());
            }
        }.runTaskLater(Main.inst(), 0);
    }
}
