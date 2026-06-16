package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractGameTeam {

    protected List<Player> players;
    protected Location spawn;
    protected BoundingBox boundingBox;

    public AbstractGameTeam(Location spawn, BoundingBox boundingBox) {
        this.players = new ArrayList<>();
        this.spawn = spawn;
        this.boundingBox = boundingBox;
    }
}
