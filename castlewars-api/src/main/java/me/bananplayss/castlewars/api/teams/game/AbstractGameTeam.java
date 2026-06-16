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
    protected BoundingBox boundingBox;

    public AbstractGameTeam(AbstractTeam team, Location spawn, BoundingBox boundingBox) {
        this.team = team;
        this.players = new ArrayList<>();
        this.spawn = spawn;
        this.boundingBox = boundingBox;
    }
}
