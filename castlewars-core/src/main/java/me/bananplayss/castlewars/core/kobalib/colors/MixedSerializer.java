package me.bananplayss.castlewars.core.kobalib.colors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;

public final class MixedSerializer {

    private MixedSerializer() {}

    public static String serialize(Component c) {
        if (subtreeLegacyExpressible(c)) {
            return ColorParser.LEGACY.serialize(c);
        }
        if (nodeNeedsMiniMessage(c)) {
            return ColorParser.MM.serialize(c);
        }
        // Root is legacy-OK but some descendant needs MM: serialize root shallowly via legacy, recurse into children
        Component shallow = (c instanceof TextComponent tc)
                ? Component.text(tc.content()).style(c.style())
                : Component.empty().style(c.style());

        StringBuilder sb = new StringBuilder();
        sb.append(ColorParser.LEGACY.serialize(shallow));
        for (Component child : c.children()) {
            sb.append(serialize(child));
        }
        return sb.toString();
    }

    private static boolean nodeNeedsMiniMessage(Component n) {
        if (!(n instanceof TextComponent)) {
            return true;
        }
        Style style = n.style();
        return style.clickEvent() != null
                || style.hoverEvent() != null
                || style.insertion() != null
                || style.font() != null;
    }

    private static boolean subtreeLegacyExpressible(Component n) {
        if (nodeNeedsMiniMessage(n)) {
            return false;
        }
        for (Component child : n.children()) {
            if (!subtreeLegacyExpressible(child)) {
                return false;
            }
        }
        return true;
    }
}
