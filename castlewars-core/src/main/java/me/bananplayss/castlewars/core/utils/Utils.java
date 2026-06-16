package me.bananplayss.castlewars.core.utils;

import com.cryptomorin.xseries.XItemStack;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static ItemStack getItemFromConfig(ConfigurationSection section) {
        XItemStack.Deserializer des = XItemStack.deserializer().fromConfig(section).withMiniMessage(str -> str.stream().map(ColorParser::parse).toList());

        return des.deserialize();
    }
}
