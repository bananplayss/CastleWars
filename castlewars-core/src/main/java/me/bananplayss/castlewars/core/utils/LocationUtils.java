package me.bananplayss.castlewars.core.utils;

import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.Arrays;

public class LocationUtils {
    public static Location toLocation(World world, Vector3i vector3i){
        return new Location(world,vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public static Location parse(String string) { // world, x, y, z, yaw, pitch
        if (string == null) return null;
        String[] args = string.split(", ");
        if (args.length == 0) return null;
        World world = Bukkit.getWorld(args[0]);

        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length == 3) {
            return new Location(
                    world,
                    Double.parseDouble(args[0]),
                    Double.parseDouble(args[1]),
                    Double.parseDouble(args[2])
            );
        } else if (args.length == 5) {
            return new Location(
                    world,
                    Double.parseDouble(args[0]),
                    Double.parseDouble(args[1]),
                    Double.parseDouble(args[2]),
                    Float.parseFloat(args[3]),
                    Float.parseFloat(args[4])
            );
        }
        return null;
    }

    public static String toString(Location location) {
        return toString(location, false, true);
    }

    public static String toString(Location location, boolean blockOnly, boolean rotation) {
        StringBuilder builder = new StringBuilder(location.getWorld().getName()).append(", ");
        if (blockOnly) {
            builder.append(location.getBlockX())
                    .append(", ")
                    .append(location.getBlockY())
                    .append(", ")
                    .append(location.getBlockZ());
        } else {
            builder.append(location.getX())
                    .append(", ")
                    .append(location.getY())
                    .append(", ")
                    .append(location.getZ());
        }

        if(rotation) {
            builder.append(", ").append(location.getYaw()).append(", ").append(location.getPitch());
        }

        return builder.toString();
    }

    public static boolean isInside(Location location, BoundingBox boundingBox) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        return x >= boundingBox.getMinX() && x <= boundingBox.getMaxX()
                && y >= boundingBox.getMinY() && y <= boundingBox.getMaxY()
                && z >= boundingBox.getMinZ() && z <= boundingBox.getMaxZ();
    }

    public static boolean isInside(Player player, Location loc1, Location loc2) {
        if (!player.getWorld().equals(loc1.getWorld()) || !loc1.getWorld().equals(loc2.getWorld())) return false;

        Location p = player.getLocation();

        double minX = Math.min(loc1.getX(), loc2.getX());
        double maxX = Math.max(loc1.getX(), loc2.getX());

        double minY = Math.min(loc1.getY(), loc2.getY());
        double maxY = Math.max(loc1.getY(), loc2.getY());

        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return p.getX() >= minX && p.getX() <= maxX
                && p.getY() >= minY && p.getY() <= maxY
                && p.getZ() >= minZ && p.getZ() <= maxZ;
    }
}
