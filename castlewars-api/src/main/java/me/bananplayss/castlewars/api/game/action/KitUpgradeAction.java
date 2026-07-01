package me.bananplayss.castlewars.api.game.action;

import me.bananplayss.castlewars.api.game.Game;
import net.kyori.adventure.text.Component;

public class KitUpgradeAction extends GameAction {

    private final String kit;

    public KitUpgradeAction(String displayName, String kit, long delay) {
        super(displayName, delay);

        this.kit = kit;
    }

    @Override
    public void apply(Game game) {
        game.setKitName(this.kit);
    }
}
