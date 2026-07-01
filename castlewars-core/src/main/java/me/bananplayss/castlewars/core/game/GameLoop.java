package me.bananplayss.castlewars.core.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;

@Getter
@AllArgsConstructor
public class GameLoop implements Runnable {

    private final Game game;

    @Override
    public void run() {
        this.game.getPhaseManager().tick();

        Main.getInstance().getScoreboardManager().updateScoreboard(this.game);
    }
}
