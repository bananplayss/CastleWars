package me.bananplayss.castlewars.api.game.phases;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import org.bukkit.Bukkit;

@Getter
public class WaitingPhase extends AbstractPhase {

    public WaitingPhase(Game game, long duration) {
        super(game, duration);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onUpdate() {
        if(this.game.getAllPlayers().size() == this.game.getBaseArena().getMaxPlayerCount()) {
            this.game.getPhaseManager().nextPhase(false, true);
            System.out.println("Starting?");
        }
    }

    @Override
    public void onEnd() {
        this.game.reset();
//        if(this.game.getAllPlayers().size() < this.game.getBaseArena().getTeamSize()) {
//            System.out.println("Gatya nincs elég player reset");
//        }
    }
}
