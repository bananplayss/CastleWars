package me.bananplayss.castlewars.core.kobalib.colors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public final class ColorParser {

    public static final MiniMessage MM = MiniMessage.miniMessage();
    public static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .build();

    private ColorParser() {}

    public static Component parse(String input) {
        return parse("", input, null);
    }

    public static Component parse(String prefix, String input) {
        return parse(prefix, input, null);
    }

    public static Component parse(String input, @Nullable Player context) {
        String processed = PapiBridge.apply(input, context);
        String translated = LegacyToMiniMessage.translate(processed);
        return MM.deserialize(translated);
    }

    public static Component parse(String prefix, String input, @Nullable Player context) {
        String processed = PapiBridge.apply(input, context);
        String translated = LegacyToMiniMessage.translate(processed);
        return MM.deserialize(prefix + translated);
    }

    public static String toString(Component component) {
        return MixedSerializer.serialize(component);
    }
}
