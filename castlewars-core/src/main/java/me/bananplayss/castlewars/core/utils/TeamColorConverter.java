package me.bananplayss.castlewars.core.utils;

import org.bukkit.Color;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamColorConverter {


    private TeamColorConverter() {
    }

    private static final Pattern HEX_PATTERN = Pattern.compile(
            "(?i)(?:<\\s*#([0-9a-f]{6})\\s*>|&#([0-9a-f]{6})|#([0-9a-f]{6})|0x([0-9a-f]{6})|^([0-9a-f]{6})$)"
    );

    private static final Map<String, Color> NAMED_COLORS = new HashMap<>();
    private static final Map<Character, Color> LEGACY_COLORS = new HashMap<>();

    static {
        // Minecraft legacy colors
        register("black", Color.fromRGB(0x000000), '0');
        register("dark_blue", Color.fromRGB(0x0000AA), '1');
        register("dark_green", Color.fromRGB(0x00AA00), '2');
        register("dark_aqua", Color.fromRGB(0x00AAAA), '3');
        register("dark_red", Color.fromRGB(0xAA0000), '4');
        register("dark_purple", Color.fromRGB(0xAA00AA), '5');
        register("gold", Color.fromRGB(0xFFAA00), '6');
        register("gray", Color.fromRGB(0xAAAAAA), '7');
        register("dark_gray", Color.fromRGB(0x555555), '8');
        register("blue", Color.fromRGB(0x5555FF), '9');
        register("green", Color.fromRGB(0x55FF55), 'a');
        register("aqua", Color.fromRGB(0x55FFFF), 'b');
        register("red", Color.fromRGB(0xFF5555), 'c');
        register("light_purple", Color.fromRGB(0xFF55FF), 'd');
        register("yellow", Color.fromRGB(0xFFFF55), 'e');
        register("white", Color.fromRGB(0xFFFFFF), 'f');

        // Common aliases
        alias("grey", "gray");
        alias("dark_grey", "dark_gray");
        alias("purple", "dark_purple");
        alias("pink", "light_purple");
        alias("magenta", "light_purple");
        alias("orange", "gold");
        alias("cyan", "aqua");
        alias("lime", "green");
    }

    private static void register(String name, Color color, char legacyCode) {
        NAMED_COLORS.put(normalizeName(name), color);
        LEGACY_COLORS.put(Character.toLowerCase(legacyCode), color);
    }

    private static void alias(String alias, String target) {
        NAMED_COLORS.put(normalizeName(alias), NAMED_COLORS.get(normalizeName(target)));
    }

    /**
     * Parses a color string into Bukkit Color.
     * <p>
     * Supported:
     * <green>, green, dark_red, &a, §a, &#FF0000, <#FF0000>, #FF0000, FF0000, 0xFF0000
     */
    public static Optional<Color> parseColor(String input) {
        if (input == null) {
            return Optional.empty();
        }

        String raw = input.trim();

        if (raw.isEmpty()) {
            return Optional.empty();
        }

        // Legacy: &a / §a
        Optional<Color> legacy = parseLegacyColor(raw);
        if (legacy.isPresent()) {
            return legacy;
        }

        // Hex: &#FF0000 / <#FF0000> / #FF0000 / FF0000 / 0xFF0000
        Optional<Color> hex = parseHexColor(raw);
        if (hex.isPresent()) {
            return hex;
        }

        // Named: <green> / green / dark_red
        Optional<Color> named = parseNamedColor(raw);
        if (named.isPresent()) {
            return named;
        }

        return Optional.empty();
    }

    /**
     * Same as parseColor, but throws if invalid.
     */
    public static Color parseColorOrThrow(String input) {
        return parseColor(input).orElseThrow(() -> new IllegalArgumentException("Invalid color: " + input));
    }

    /**
     * Same as parseColor, but returns fallback if invalid.
     */
    public static Color parseColorOrDefault(String input, Color fallback) {
        return parseColor(input).orElse(fallback);
    }

    private static Optional<Color> parseLegacyColor(String raw) {
        if (raw.length() < 2) {
            return Optional.empty();
        }

        char first = raw.charAt(0);

        if (first != '&' && first != '§') {
            return Optional.empty();
        }

        char code = Character.toLowerCase(raw.charAt(1));

        return Optional.ofNullable(LEGACY_COLORS.get(code));
    }

    private static Optional<Color> parseHexColor(String raw) {
        Matcher matcher = HEX_PATTERN.matcher(raw);

        if (!matcher.find()) {
            return Optional.empty();
        }

        String hex = null;

        for (int i = 1; i <= matcher.groupCount(); i++) {
            if (matcher.group(i) != null) {
                hex = matcher.group(i);
                break;
            }
        }

        if (hex == null) {
            return Optional.empty();
        }

        int rgb = Integer.parseInt(hex, 16);
        return Optional.of(Color.fromRGB(rgb));
    }

    private static Optional<Color> parseNamedColor(String raw) {
        String normalized = normalizeName(stripMiniMessageBrackets(raw));
        return Optional.ofNullable(NAMED_COLORS.get(normalized));
    }

    private static String stripMiniMessageBrackets(String raw) {
        String value = raw.trim();

        if (value.startsWith("<") && value.endsWith(">")) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }

    private static String normalizeName(String input) {
        return input
                .trim()
                .toLowerCase(Locale.ROOT)
                .replace("-", "_")
                .replace(" ", "_");
    }
}

