package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.teams.FlagTeam;
import me.bananplayss.castlewars.api.teams.ZoneTeam;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

@Setter
@Getter
public class GameZoneTeam extends AbstractGameTeam {

    private float progress;

    public GameZoneTeam(Location spawn, Location corner1, Location corner2, ZoneTeam team) {
        super(team, spawn, corner1, corner2);
        this.team = team;
        this.progress = 0;
    }

    @Override
    public ZoneTeam getTeam() {
        return (ZoneTeam) super.getTeam();
    }
}
