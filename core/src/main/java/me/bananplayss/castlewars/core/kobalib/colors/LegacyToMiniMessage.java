package me.bananplayss.castlewars.core.kobalib.colors;

import java.util.Map;

public final class LegacyToMiniMessage {

    private static final Map<Character, String> COLOR_MAP = Map.ofEntries(
            Map.entry('0', "black"),
            Map.entry('1', "dark_blue"),
            Map.entry('2', "dark_green"),
            Map.entry('3', "dark_aqua"),
            Map.entry('4', "dark_red"),
            Map.entry('5', "dark_purple"),
            Map.entry('6', "gold"),
            Map.entry('7', "gray"),
            Map.entry('8', "dark_gray"),
            Map.entry('9', "blue"),
            Map.entry('a', "green"),
            Map.entry('b', "aqua"),
            Map.entry('c', "red"),
            Map.entry('d', "light_purple"),
            Map.entry('e', "yellow"),
            Map.entry('f', "white")
    );

    private static final Map<Character, String> FORMAT_MAP = Map.of(
            'k', "obfuscated",
            'l', "bold",
            'm', "strikethrough",
            'n', "underlined",
            'o', "italic"
    );

    private LegacyToMiniMessage() {}

    public static String translate(String input) {
        String normalized = input.replace('§', '&');

        StringBuilder out = new StringBuilder(normalized.length() * 2);
        int i = 0;
        while (i < normalized.length()) {
            char c = normalized.charAt(i);

            if (c == '&' && i + 1 < normalized.length()) {
                char next = normalized.charAt(i + 1);

                // &#rrggbb hex color
                if (next == '#' && i + 8 <= normalized.length()) {
                    String hex = normalized.substring(i + 2, i + 8);
                    if (isHex(hex)) {
                        out.append('<').append('#').append(hex).append('>');
                        i += 8;
                        continue;
                    }
                }

                char lower = Character.toLowerCase(next);

                // Named color
                String colorTag = COLOR_MAP.get(lower);
                if (colorTag != null) {
                    out.append('<').append(colorTag).append('>');
                    i += 2;
                    continue;
                }

                // Formatting
                String formatTag = FORMAT_MAP.get(lower);
                if (formatTag != null) {
                    out.append('<').append(formatTag).append('>');
                    i += 2;
                    continue;
                }

                // Reset
                if (lower == 'r') {
                    out.append("<reset>");
                    i += 2;
                    continue;
                }

                // Unknown — pass through literally
            }

            out.append(c);
            i++;
        }

        return out.toString();
    }

    private static boolean isHex(String s) {
        if (s.length() != 6) return false;
        for (int i = 0; i < 6; i++) {
            char c = s.charAt(i);
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                return false;
            }
        }
        return true;
    }
}
