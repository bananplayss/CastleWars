package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.teams.FlagTeam;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

@Setter
@Getter
public class GameFlagTeam extends AbstractGameTeam {

    private final FlagTeam team;
    private Location flagSpawn;

    private int progress;

    public GameFlagTeam(Location spawn, BoundingBox boundingBox, FlagTeam team) {
        super(spawn, boundingBox);
        this.team = team;
        this.progress = 0;
    }
}
