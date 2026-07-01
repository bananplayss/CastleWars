package me.bananplayss.castlewars.api.game.action;

import me.bananplayss.castlewars.api.game.Game;

public class BannerGoneAction  extends GameAction {

    public BannerGoneAction(String displayName, long delay) {
        super(displayName, delay);
    }

    @Override
    public void apply(Game game) {
        System.out.println("Banner gone");
    }
}