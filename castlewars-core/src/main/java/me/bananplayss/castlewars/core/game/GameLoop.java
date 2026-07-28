package me.bananplayss.castlewars.core.game;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.FlagProtectionEffect;
import me.bananplayss.castlewars.core.kobalib.Tasks;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

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
        this.game.getActionManager().tick();
        Main.getInstance().getScoreboardManager().updateScoreboard(this.game);

        if(this.game instanceof FlagGameImpl fg) {
            fg.getFlagResetManager().tick();

//            for (Map.Entry<Location, GameFlagTeam> entry : fg.getFlagManager().getBlockFlags().entrySet()) {
//                GameFlagTeam ft = entry.getValue();
//                ft.getCurrentEffect()
//                if(entry.getValue().getFlagProtection() >= System.currentTimeMillis()) {
//
//                } else {
//                    if (ft.getCurrentEffect() instanceof FlagProtectionEffect) {
//
//                    }
//                }
//            }
        }

        if (this.game.getPhaseManager().getCurrentPhase() instanceof RunningPhase) {
            List<AbstractGameTeam> winningTeams = this.game.getTeams().values().stream().filter(t -> !t.isDead()).toList();
            if (winningTeams.size() == 1) {
                System.out.println("A kurva anyad team 1 maradt a kurva win faszomat");
                this.game.end(winningTeams.get(0));
            } else if (winningTeams.isEmpty()) {
                //rip game
                System.out.println("Nem vagyok buzi! TÁTVA VAN A SZÁJA GECI CSUROG LE A TORKÁN");
                this.game.reset();
            }
        }
    }
}
