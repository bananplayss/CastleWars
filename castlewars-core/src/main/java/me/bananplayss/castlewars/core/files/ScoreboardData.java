package me.bananplayss.castlewars.core.files;

import lombok.*;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.GameMode;
import me.bananplayss.castlewars.api.game.phases.CelebrationPhase;
import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.game.phases.StartingPhase;
import me.bananplayss.castlewars.api.game.phases.WaitingPhase;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
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

    private String you;

    private final Map<ScoreboardType, Scoreboard> defaultScoreboards;
    private final Map<GameMode, Map<ScoreboardType, Scoreboard>> scoreboards;
    private final Map<GameFlagTeam.FlagState, String> captureTheFlagFormats;

    public ScoreboardData() {
        this.defaultScoreboards = new HashMap<>();
        this.scoreboards = new HashMap<>();
        this.captureTheFlagFormats = new HashMap<>();
    }

    public void reload() {
        FileConfiguration config = Main.getInstance().getFileManager().getScoreboards().getConfig();

        this.you = config.getString("you");

        for (String key : config.getConfigurationSection("formats.capture_the_flag.flag_states").getKeys(false)) {
            this.captureTheFlagFormats.put(GameFlagTeam.FlagState.valueOf(key.toUpperCase()), config.getString("formats.capture_the_flag.flag_states." + key));
        }


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
        if (game.getPhaseManager().getCurrentPhase() instanceof WaitingPhase) {
            type = ScoreboardType.WAITING;
        } else if (game.getPhaseManager().getCurrentPhase() instanceof StartingPhase) {
            type = ScoreboardType.STARTING;
        } else if (game.getPhaseManager().getCurrentPhase() instanceof RunningPhase) {
            type = ScoreboardType.RUNNING;
        } else if (game.getPhaseManager().getCurrentPhase() instanceof CelebrationPhase) {
            type = ScoreboardType.CELEBRATION;
        }

        if (type == null) return null;

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
