package me.bananplayss.castlewars.core.utils;

import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static Location toLocation(World world, Vector3i vector3i){
        return new Location(world,vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public boolean isInside(Location location, Location other) {
        if(location == null || other == null) return false;
        if(location.getWorld() != other.getWorld()) return false;
        int maxX = Math.max(location.getBlockX(), other.getBlockX());
        int minX = Math.min(location.getBlockX(), other.getBlockX());

        int maxY = Math.max(location.getBlockY(), other.getBlockY());
        int minY = Math.min(location.getBlockY(), other.getBlockY());

        int maxZ = Math.max(location.getBlockZ(), other.getBlockZ());
        int minZ = Math.min(location.getBlockZ(), other.getBlockZ());
        return maxX <= minX && maxY <= minY && maxZ <= minZ;
    }
}
