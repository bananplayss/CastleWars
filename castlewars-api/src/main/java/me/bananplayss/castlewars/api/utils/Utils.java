package me.bananplayss.castlewars.api.utils;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class Utils {
    public static Location findNearestBannerLocation(Location deathLocation, int maxRadius) {
        World world = deathLocation.getWorld();

        // Find ground below the death location
        int startY = deathLocation.getBlockY();

        while (startY > world.getMinHeight()) {
            Block ground = world.getBlockAt(deathLocation.getBlockX(), startY - 1, deathLocation.getBlockZ());

            if (ground.getType().isSolid()) {
                break;
            }

            startY--;
        }

        // Spiral search
        for (int radius = 0; radius <= maxRadius; radius++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {

                    // Only check the edge of the square
                    if (Math.abs(x) != radius && Math.abs(z) != radius)
                        continue;

                    Block feet = world.getBlockAt(deathLocation.getBlockX() + x, startY, deathLocation.getBlockZ() + z);

                    if (canPlaceBanner(feet)) {
                        return feet.getLocation();
                    }
                }
            }
        }
        return null;
    }

    private static boolean canPlaceBanner(Block feet) {
        Block ground = feet.getRelative(BlockFace.DOWN);
        Block head = feet.getRelative(BlockFace.UP);

        return ground.getType().isSolid()
                && feet.isEmpty()
                && head.isEmpty();
    }

    private static boolean isReplaceableDecoration(Block block) {
        XMaterial type = XMaterial.matchXMaterial(block.getType());

        return switch (type) {
            case SHORT_GRASS,
                 TALL_GRASS,
                 FERN,
                 LARGE_FERN,
                 DANDELION,
                 POPPY,
                 BLUE_ORCHID,
                 ALLIUM,
                 AZURE_BLUET,
                 RED_TULIP,
                 ORANGE_TULIP,
                 WHITE_TULIP,
                 PINK_TULIP,
                 OXEYE_DAISY,
                 CORNFLOWER,
                 LILY_OF_THE_VALLEY,
                 TORCHFLOWER,
                 PITCHER_PLANT,
                 DEAD_BUSH,
                 SNOW -> true;

            default -> false;
        };
    }

    public static BlockFace getNearestBlockFace(Player player) {

        float yaw = player.getLocation().getYaw();

        yaw = (yaw % 360 + 360) % 360;

        int index = (int) Math.round(yaw / 22.5) % 16;

        return switch (index) {
            case 0  -> BlockFace.SOUTH;
            case 1  -> BlockFace.SOUTH_SOUTH_WEST;
            case 2  -> BlockFace.SOUTH_WEST;
            case 3  -> BlockFace.WEST_SOUTH_WEST;
            case 4  -> BlockFace.WEST;
            case 5  -> BlockFace.WEST_NORTH_WEST;
            case 6  -> BlockFace.NORTH_WEST;
            case 7  -> BlockFace.NORTH_NORTH_WEST;
            case 8  -> BlockFace.NORTH;
            case 9  -> BlockFace.NORTH_NORTH_EAST;
            case 10 -> BlockFace.NORTH_EAST;
            case 11 -> BlockFace.EAST_NORTH_EAST;
            case 12 -> BlockFace.EAST;
            case 13 -> BlockFace.EAST_SOUTH_EAST;
            case 14 -> BlockFace.SOUTH_EAST;
            case 15 -> BlockFace.SOUTH_SOUTH_EAST;
            default -> BlockFace.SOUTH;
        };
    }
}