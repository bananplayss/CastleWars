package me.bananplayss.castlewars.core.utils;

import me.bananplayss.castlewars.core.utils.vectors.Vector3i;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static Location toLocation(World world, Vector3i vector3i){
        return new Location(world,vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

}
