package me.bananplayss.castlewars.api.game.action;

import me.bananplayss.castlewars.api.game.Game;

/**
 * kinda time display class
 */
public class GameEndGameAction extends GameAction {

    public GameEndGameAction(String displayName, long delay) {
        super(displayName, delay);
    }

    @Override
    public void apply(Game game) {

    }
}
