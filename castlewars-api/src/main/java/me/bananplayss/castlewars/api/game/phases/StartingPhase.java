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
        if(this.game.getAllPlayers().size() < this.game.getBaseArena().getMaxPlayerCount()) {
            this.game.getPhaseManager().previousPhase(false, true);
            System.out.println("Switch back to waiting");
        }
    }

    @Override
    public void onEnd() {
        if(this.game.getAllPlayers().size() < this.game.getBaseArena().getMaxPlayerCount()) {
            System.out.println("Gatya nincs elég player reset");
            this.game.reset();
        }
    }
}
