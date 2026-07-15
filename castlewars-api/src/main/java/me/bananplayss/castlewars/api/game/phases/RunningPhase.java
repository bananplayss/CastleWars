package me.bananplayss.castlewars.api.game.phases;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.action.GameEndGameAction;

@Getter
public class RunningPhase extends AbstractPhase {

    public RunningPhase(Game game, long duration) {
        super(game, duration);
    }

    @Override
    public void onStart() {
        this.game.start();
        this.game.getActionManager().start();
        this.game.getActionManager().getActions().add(new GameEndGameAction("Game End", this.end - System.currentTimeMillis()));
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onEnd() {
        // this.game.start();

    }
}