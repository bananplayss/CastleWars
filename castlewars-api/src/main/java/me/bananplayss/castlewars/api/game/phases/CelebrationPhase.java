package me.bananplayss.castlewars.api.game.phases;

import me.bananplayss.castlewars.api.game.Game;

public class CelebrationPhase extends AbstractPhase {

    public CelebrationPhase(Game game, long duration) {
        super(game, duration);
    }

    @Override
    public void onStart() {
        System.out.println("CELEBRATION ELINDULTXDDDD");
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onEnd() {
        // this.game.start();
        this.game.reset();
//        System.out.println("RESET GAME");
    }
}