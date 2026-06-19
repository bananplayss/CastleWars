package me.bananplayss.castlewars.core.utils;

import com.cryptomorin.xseries.XItemStack;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static ItemStack getItemFromConfig(ConfigurationSection section) {
        XItemStack.Deserializer des = XItemStack.deserializer().fromConfig(section).withMiniMessage(str -> str.stream().map(ColorParser::parse).toList());

        return des.deserialize();
    }

    public static Material getBannerByBaseColor(DyeColor color) {
        return switch (color) {
            case WHITE -> Material.WHITE_BANNER;
            case ORANGE -> Material.ORANGE_BANNER;
            case MAGENTA -> Material.MAGENTA_BANNER;
            case LIGHT_BLUE -> Material.LIGHT_BLUE_BANNER;
            case YELLOW -> Material.YELLOW_BANNER;
            case LIME -> Material.LIME_BANNER;
            case PINK -> Material.PINK_BANNER;
            case GRAY -> Material.GRAY_BANNER;
            case LIGHT_GRAY -> Material.LIGHT_GRAY_BANNER;
            case CYAN -> Material.CYAN_BANNER;
            case PURPLE -> Material.PURPLE_BANNER;
            case BLUE -> Material.BLUE_BANNER;
            case BROWN -> Material.BROWN_BANNER;
            case GREEN -> Material.GREEN_BANNER;
            case RED -> Material.RED_BANNER;
            case BLACK -> Material.BLACK_BANNER;
        };
    }
}
