package me.bananplayss.castlewars.core.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.core.Main;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameLoop implements Runnable {

    private final Game game;

    @Override
    public void run() {
        if (this.game.getAllPlayers().isEmpty() && this.game.getPhaseManager().getCurrentPhase() instanceof RunningPhase) {
            System.out.println("A kurva anyad hard reset 0 player geci fastzopm");
            this.game.reset();
        }


        this.game.getPhaseManager().tick();
        Main.getInstance().getScoreboardManager().updateScoreboard(this.game);

        if(this.game.getPhaseManager().getCurrentPhase() instanceof RunningPhase) {
            List<AbstractGameTeam> winningTeams = this.game.getTeams().values().stream().filter(t -> !t.isDead()).toList();
            if (winningTeams.size() == 1) {
                System.out.println("A kurva anyad team 1 maradt a kurva win faszomat");
                this.game.end(winningTeams.get(0));
            } else if(winningTeams.isEmpty()) {
                //rip game
                System.out.println("Nem vagyok buzi! TÁTVA VAN A SZÁJA GECI CSUROG LE A TORKÁN");
                this.game.reset();
            }
        }
    }
}
