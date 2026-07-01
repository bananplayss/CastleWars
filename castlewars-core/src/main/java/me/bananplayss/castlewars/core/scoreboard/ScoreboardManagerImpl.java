package me.bananplayss.castlewars.core.scoreboard;

import fr.mrmicky.fastboard.adventure.FastBoard;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.scoreboards.ScoreboardManager;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.files.ScoreboardData;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class ScoreboardManagerImpl implements ScoreboardManager {

    private final Map<UUID, FastBoard> boards;

    public ScoreboardManagerImpl() {
        this.boards = new HashMap<>();
    }

    public void show(Player player, Game game) {
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);

        updateScoreboard(game, player);
    }

    public void delete(Player player) {
        FastBoard board = this.boards.get(player.getUniqueId());
        if (board != null)
            board.delete();
        this.boards.remove(player.getUniqueId());
    }

    public void updateScoreboard(Game game) {
        ScoreboardData.Scoreboard currSb = Main.getInstance().getConfigData().getScoreboardData().getGameScoreboard(game);
        if (currSb == null) return;

        for (Player allPlayer : game.getAllPlayers()) {
            formatLines(currSb, game, allPlayer);
        }
    }

    public void updateScoreboard(Game game, Player player) {
        ScoreboardData.Scoreboard currSb = Main.getInstance().getConfigData().getScoreboardData().getGameScoreboard(game);
        if (currSb == null) return;

        formatLines(currSb, game, player);
    }

    private void formatLines(ScoreboardData.Scoreboard currSb, Game game, Player player) {
        List<Component> lines = new ArrayList<>();

        Map<AbstractGameTeam, String> flagStatus = new HashMap<>();
        Map<AbstractGameTeam, Integer> score = new HashMap<>();
        if(game instanceof FlagGame fg) {
            for (AbstractGameTeam value : game.getTeams().values()) {
                GameFlagTeam ft = (GameFlagTeam) value;

                score.put(ft, ft.getScore());
                switch (ft.getFlagState()) {
                    case SPAWN -> flagStatus.put(value, "S");
                    case DROPPED -> flagStatus.put(value, "D");
                    case CARRYING -> flagStatus.put(value, "C");
                }
            }
        }

        for (String line : currSb.getLines()) {
            if (line.equals("%teams%")) {
                for (AbstractGameTeam value : game.getTeams().values()) {
                    AbstractGameTeam t = game.getTeam(player);
                    String l = value.getTeam().getPrefix() + " " + value.getTeam().getDisplayName() + "&f: " + score.get(value) + " &f| &f" + flagStatus.get(value);
                    if(t == value) {
                        l += " X";
                    }
                    lines.add(ColorParser.parse(l, player));
                }
                continue;
            }
            line = Utils.replacePlaceholders(line, game, player);
            lines.add(ColorParser.parse(line, player));
        }

        Component title = ColorParser.parse(currSb.getTitle(), player);

        this.boards.get(player.getUniqueId()).updateTitle(title);
        this.boards.get(player.getUniqueId()).updateLines(lines);
    }
}
