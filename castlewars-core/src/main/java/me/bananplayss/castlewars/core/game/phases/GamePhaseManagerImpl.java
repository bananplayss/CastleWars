package me.bananplayss.castlewars.core.game.phases;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.game.phases.AbstractPhase;
import me.bananplayss.castlewars.api.game.phases.GamePhaseManager;
import me.bananplayss.castlewars.api.game.phases.WaitingPhase;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GamePhaseManagerImpl implements GamePhaseManager {

    private final List<AbstractPhase> phases;
    private int currentIndex;

    private AbstractPhase currentPhase;

    @Setter
    private long gameStarted;
    @Setter
    private long gameEnd;

    public GamePhaseManagerImpl() {
        this.phases = new ArrayList<>();
        this.currentIndex = -1;
    }

    public void previousPhase(boolean fireEnd, boolean fireStart) {
        if (this.currentPhase != null && fireEnd) {
            this.currentPhase.onEnd();
        }

        this.currentPhase = this.phases.get(--this.currentIndex);
        if (fireStart) {
            this.currentPhase.start();
            this.currentPhase.onStart();
        }
    }

    public void nextPhase(boolean fireEnd, boolean fireStart) {
        if (this.phases.isEmpty()) return;

        if (this.currentPhase != null && this.currentPhase.isFinished() && fireEnd) {
            this.currentPhase.onEnd();
        }

        try {
            this.currentPhase = this.phases.get(++this.currentIndex);
            if (fireStart) {
                this.currentPhase.start();
                this.currentPhase.onStart();
            }
            System.out.println("Next phase: " + this.currentPhase.getClass().getSimpleName());
        } catch (Exception e) {
//            if(this.currentPhase != null)
//                this.currentPhase.onEnd();
        }
    }

    public void tick() {
        if (this.currentPhase == null) return;

        if (this.currentPhase.isFinished()) {
            nextPhase(true, true);
            return;
        }

        this.currentPhase.onUpdate();
    }

    public AbstractPhase getNextPhase() {
        try {
            return this.phases.get(this.currentIndex + 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public long getRemainingUntilNext() {
        return this.currentPhase.getEnd() - System.currentTimeMillis();
    }
}
