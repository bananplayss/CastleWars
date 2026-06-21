package me.bananplayss.castlewars.api.utils;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
}