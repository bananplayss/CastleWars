package me.bananplayss.castlewars.api.game.phases;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;

@Getter
public class RunningPhase extends AbstractPhase {

    public RunningPhase(Game game, long duration) {
        super(game, duration);
    }

    @Override
    public void onStart() {
        this.game.start();
        this.game.getActionManager().start();
    }

    @Override
    public void onUpdate() {
        this.game.getActionManager().tick();
    }

    @Override
    public void onEnd() {
        // this.game.start();
    }
}