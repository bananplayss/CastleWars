package me.bananplayss.castlewars.core.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;

public class ColorUtils {

    public static DyeColor fromHex(String input) {
        if (input == null) return DyeColor.WHITE;

        Color rgb = Color.fromRGB(
                Integer.valueOf(input.substring(1, 3), 16),
                Integer.valueOf(input.substring(3, 5), 16),
                Integer.valueOf(input.substring(5, 7), 16)
        );

        return closestDyeColor(rgb);
    }

    private static DyeColor closestDyeColor(Color color) {
        DyeColor closest = DyeColor.WHITE;
        double bestDistance = Double.MAX_VALUE;

        for (DyeColor dye : DyeColor.values()) {
            Color c = dye.getColor();

            double distance = Math.pow(c.getRed() - color.getRed(), 2)
                    + Math.pow(c.getGreen() - color.getGreen(), 2)
                    + Math.pow(c.getBlue() - color.getBlue(), 2);

            if (distance < bestDistance) {
                bestDistance = distance;
                closest = dye;
            }
        }

        return closest;
    }
}
