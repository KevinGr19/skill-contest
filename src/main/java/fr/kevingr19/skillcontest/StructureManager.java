package fr.kevingr19.skillcontest;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.utils.ChunkCoords;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftChunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that handles structures.
 * Can detect when a player is inside a structure and store non-natural chests inside structures, for event purposes.
 */

public class StructureManager extends BukkitRunnable implements Listener {

    private final Map<ChunkCoords, Set<Tuple<StructurePiece, StructureStart>>> structurePieces = new HashMap<>();
    private final Set<Vec3i> nonNaturalChests = new HashSet<>();

    public void initialize(){
        structurePieces.clear();
        nonNaturalChests.clear();

        for(World world : Bukkit.getWorlds()) for(Chunk chunk : world.getLoadedChunks())
            onChunkLoad(new ChunkLoadEvent(chunk, false));
    }

    @EventHandler
    public void onChunkLoad(final ChunkLoadEvent e){
        final LevelChunk levelChunk = ((CraftChunk) e.getChunk()).getHandle();
        for(StructureStart structureStart : levelChunk.getAllStarts().values())
        {

            for(StructurePiece piece : structureStart.getPieces()){
                final int xB = piece.getBoundingBox().minX() >> 4;
                final int zB = piece.getBoundingBox().minZ() >> 4;
                final int xE = (int) Math.ceil(piece.getBoundingBox().maxX() / 16.0);
                final int zE = (int) Math.ceil(piece.getBoundingBox().maxZ() / 16.0);

                for(int x = xB; x < xE; x++) for(int z = zB; z < zE; z++){
                    ChunkCoords coords = new ChunkCoords(e.getWorld(), x, z);

                    if(!structurePieces.containsKey(coords)) structurePieces.put(coords, new HashSet<>());
                    structurePieces.get(coords).add(new Tuple<>(piece, structureStart));
                }
            }
        }
    }

    @EventHandler
    public void onChestPlaced(final BlockPlaceEvent e){
        final Block block = e.getBlockPlaced();
        if(block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) return;

        ChunkCoords coords = ChunkCoords.fromLocation(block.getLocation());
        if(structurePieces.containsKey(coords)){
            final Vec3i pos = new Vec3i(block.getX(), block.getY(), block.getZ());

            for(Tuple<StructurePiece, StructureStart> pairValue : structurePieces.get(coords))
                if(pairValue.getA().getBoundingBox().isInside(pos)) nonNaturalChests.add(pos);
        }
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(this::checkPlayerStructure);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        checkPlayerStructure(e.getEntity());
    }

    private void checkPlayerStructure(Player player){
        final ChunkCoords coords = ChunkCoords.fromLocation(player.getLocation());
        if(!structurePieces.containsKey(coords)) return;

        final Vec3i pos = new Vec3i(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        for(Tuple<StructurePiece, StructureStart> pairValue : structurePieces.get(coords))
        {
            if(pairValue.getA().getBoundingBox().isInside(pos)){
                PlayerInsideStructureEvent event = new PlayerInsideStructureEvent(player, pairValue.getB().getStructure());
                Bukkit.getPluginManager().callEvent(event);
                return;
            }
        }
    }

    @Nullable
    public Structure getChestStructureOrigin(Location loc){
        ChunkCoords coords = ChunkCoords.fromLocation(loc);
        if(structurePieces.containsKey(coords)) {
            final Vec3i pos = new Vec3i(loc.getX(), loc.getY(), loc.getZ());
            if(nonNaturalChests.contains(pos)) return null;

            for(Tuple<StructurePiece, StructureStart> pairValue : structurePieces.get(coords))
                if(pairValue.getA().getBoundingBox().isInside(pos)) return pairValue.getB().getStructure();
        }

        return null;
    }

    public static boolean isAncientCity(Structure structure, World world){
        return world.getEnvironment() == World.Environment.NORMAL && structure instanceof JigsawStructure && structure.spawnOverrides().size() > 1;
    }

    public static boolean isVillage(Structure structure, World world){
        return world.getEnvironment() == World.Environment.NORMAL && structure instanceof JigsawStructure && structure.spawnOverrides().size() == 0;
    }

    public static boolean isPillagerOutpost(Structure structure, World world){
        return world.getEnvironment() == World.Environment.NORMAL && structure instanceof JigsawStructure && structure.spawnOverrides().size() == 1;
    }

    public static boolean isBastion(Structure structure, World world){
        return world.getEnvironment() == World.Environment.NETHER && structure instanceof JigsawStructure;
    }
}
