package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.teams.NexusTeam;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

@Setter
@Getter
public class GameNexusTeam extends AbstractGameTeam {

    private final NexusTeam team;

    private float progress;

    public GameNexusTeam(Location spawn, BoundingBox boundingBox, NexusTeam team) {
        super(spawn, boundingBox);
        this.team = team;
        this.progress = 0;
    }
}
