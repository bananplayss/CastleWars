package me.bananplayss.castlewars.core.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.GameMode;
import me.bananplayss.castlewars.api.game.phases.CelebrationPhase;
import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.game.phases.StartingPhase;
import me.bananplayss.castlewars.api.game.phases.WaitingPhase;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ScoreboardData {

    public enum ScoreboardType {
        WAITING,
        STARTING,
        RUNNING,
        CELEBRATION;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class Scoreboard {
        //        private final ScoreboardType type;
        private String title;
        private List<String> lines;
    }

    private final Map<ScoreboardType, Scoreboard> defaultScoreboards;
    private final Map<GameMode, Map<ScoreboardType, Scoreboard>> scoreboards;

    public ScoreboardData() {
        this.defaultScoreboards = new HashMap<>();
        this.scoreboards = new HashMap<>();
    }

    public void reload() {
        FileConfiguration config = Main.getInstance().getFileManager().getScoreboards().getConfig();

        ConfigurationSection defaultSection = config.getConfigurationSection("default");

        this.defaultScoreboards.clear();
        for (String key : defaultSection.getKeys(false)) {
            ScoreboardType type = ScoreboardType.valueOf(key.toUpperCase());
            this.defaultScoreboards.put(type, new Scoreboard(
//                    type,
                            defaultSection.getString(key + ".title"),
                            defaultSection.getStringList(key + ".lines"))
            );
        }

        this.scoreboards.clear();
        for (GameMode key : GameMode.values()) {
            Map<ScoreboardType, Scoreboard> sb = new HashMap<>();

            try {
                for (String s : config.getConfigurationSection(key.name().toLowerCase()).getKeys(false)) {
                    ScoreboardType type = ScoreboardType.valueOf(s.toUpperCase());

                    sb.put(type, new Scoreboard(
                            config.getString(key.name().toLowerCase() + "." + s + ".title"),
                            config.getStringList(key.name().toLowerCase() + "." + s + ".lines"))
                    );
                }

                this.scoreboards.put(key, sb);
            } catch (NullPointerException e) {
            }
        }
    }

    public Scoreboard getGameScoreboard(Game game) {
        if (game.getPhaseManager().getCurrentPhase() == null) return null;

        ScoreboardType type = null;
        if(game.getPhaseManager().getCurrentPhase() instanceof WaitingPhase) {
            type = ScoreboardType.WAITING;
        } else if (game.getPhaseManager().getCurrentPhase() instanceof StartingPhase) {
            type = ScoreboardType.STARTING;
        } else if (game.getPhaseManager().getCurrentPhase() instanceof RunningPhase) {
            type = ScoreboardType.RUNNING;
        } else if (game.getPhaseManager().getCurrentPhase() instanceof CelebrationPhase) {
            type = ScoreboardType.RUNNING;
        }

        if(type == null) return null;

        if (this.scoreboards.containsKey(game.getBaseArena().getGameMode())) {
            if (this.scoreboards.get(game.getBaseArena().getGameMode()).containsKey(type)) {
                return this.scoreboards.get(game.getBaseArena().getGameMode()).get(type);
            }
        }

        if (this.defaultScoreboards.containsKey(type)) {
            return this.defaultScoreboards.get(type);
        }

        return null;
    }
}
