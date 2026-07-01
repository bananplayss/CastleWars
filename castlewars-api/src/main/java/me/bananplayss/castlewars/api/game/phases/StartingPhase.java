package me.bananplayss.castlewars.api.game.phases;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;

@Getter
public class StartingPhase extends AbstractPhase {

    public StartingPhase(Game game, long duration) {
        super(game, duration);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onUpdate() {
        if(this.game.getAllPlayers().size() < this.game.getBaseArena().getTeamSize()) {
           // this.game.getPhaseManager().previousPhase(false, false);
        }
    }

    @Override
    public void onEnd() {
    }
}
