package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.teams.ZoneTeam;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

@Setter
@Getter
public class GameZoneTeam extends AbstractGameTeam {

    private final ZoneTeam team;

    private float progress;

    public GameZoneTeam(Location spawn, BoundingBox boundingBox, ZoneTeam team) {
        super(spawn, boundingBox);
        this.team = team;
        this.progress = 0;
    }
}
