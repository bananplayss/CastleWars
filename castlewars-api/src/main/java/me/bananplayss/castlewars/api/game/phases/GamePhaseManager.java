package me.bananplayss.castlewars.api.game.phases;

import java.util.List;

public interface GamePhaseManager {

    List<AbstractPhase> getPhases();

    AbstractPhase getCurrentPhase();
    AbstractPhase getNextPhase();

    long getRemainingUntilNext();

    long getGameStarted();
    long getGameEnd();

    void previousPhase(boolean fireEnd, boolean fireStart);

    /**
     * Azért kell a fire cuccok, hogy amikor switchelgetünk nem mindig akarunk onEnd és onStartot hívni.
     * @param fireEnd
     * @param fireStart
     */
    void nextPhase(boolean fireEnd, boolean fireStart);
    void tick();
//    void switchToWaiting();
}
