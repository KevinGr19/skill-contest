package fr.kevingr19.skillcontest.utils;

import org.bukkit.Location;
import org.bukkit.World;

public record ChunkCoords(World world, int x, int z) {

    public static ChunkCoords fromLocation(Location loc) {
        return new ChunkCoords(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ChunkCoords chunkCoords)) return false;

        return chunkCoords.x == x && chunkCoords.z == z && chunkCoords.world == world;
    }

}
