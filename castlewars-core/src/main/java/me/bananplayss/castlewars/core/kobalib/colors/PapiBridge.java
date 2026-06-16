package me.bananplayss.castlewars.core.kobalib.colors;

import me.bananplayss.castlewars.core.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public final class PapiBridge {

    public static String apply(String input, @Nullable Player ctx) {
        if (!Main.getInstance().getHookManager().isPapi() || !input.contains("%")) {
            return input;
        }

        return PlaceholderAPI.setPlaceholders(ctx, input);
    }
}
