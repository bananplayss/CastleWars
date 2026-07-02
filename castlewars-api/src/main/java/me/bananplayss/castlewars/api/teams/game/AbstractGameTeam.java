package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractGameTeam {

    protected List<Player> players;
    protected AbstractTeam team;
    protected Location spawn;
//    protected BoundingBox boundingBox;

    protected Location corner1;
    protected Location corner2;

    public AbstractGameTeam(AbstractTeam team, Location spawn, Location corner1, Location corner2) {
        this.team = team;
        this.players = new ArrayList<>();
        this.spawn = spawn;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }
}
