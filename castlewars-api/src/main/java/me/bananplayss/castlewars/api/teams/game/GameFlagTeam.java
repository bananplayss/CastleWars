package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import me.bananplayss.castlewars.api.teams.FlagTeam;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

@Setter
@Getter
public class GameFlagTeam extends AbstractGameTeam {

    public static enum FlagState {
        SPAWN,
        DROPPED,
        CARRYING;
    }

    private Location flagSpawn;
    private FlagState flagState;
    private long flagProtection;

    private int score;

    private CustomEffect ringEffect;

    public GameFlagTeam(Location spawn, FlagTeam team, Location corner1, Location corner2) {
        super(team, spawn, corner1, corner2);
        this.team = team;
        this.score = 0;
        this.flagState = FlagState.SPAWN;
    }

    public void addScore() {
        this.score++;
    }

    public boolean hasFlagProtection() {
        return this.flagProtection > System.currentTimeMillis();
    }

    @Override
    public FlagTeam getTeam() {
        return (FlagTeam) super.getTeam();
    }
}
