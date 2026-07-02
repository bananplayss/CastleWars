package me.bananplayss.castlewars.api.utils;

import java.util.regex.Pattern;

public final class ColorNormalizer {

    // &#RRGGBB
    private static final Pattern HEX_AMP = Pattern.compile("&#([A-Fa-f0-9]{6})");

    // #RRGGBB
    private static final Pattern HEX_HASH = Pattern.compile("(?<!&)#([A-Fa-f0-9]{6})");

    // MiniMessage tags like <red>, <bold>, <gradient:...>, </bold>
    private static final Pattern MINI_TAGS = Pattern.compile("<[^>]+>");

    // Legacy & color codes
    private static final Pattern LEGACY = Pattern.compile("&[0-9a-fk-orA-FK-OR]");

    private ColorNormalizer() {}

    public static String strip(String input) {
        if (input == null) return null;

        String s = input;

        // remove hex formats
        s = HEX_AMP.matcher(s).replaceAll("");
        s = HEX_HASH.matcher(s).replaceAll("");

        // remove MiniMessage tags
        s = MINI_TAGS.matcher(s).replaceAll("");

        // remove legacy colors
        s = LEGACY.matcher(s).replaceAll("");

        return s;
    }
}