package me.bananplayss.castlewars.api.teams.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.api.profiles.ProfileCache;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import org.bukkit.Location;
import org.bukkit.block.data.FaceAttachable;
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

    @Setter private boolean dead;

    public AbstractGameTeam(AbstractTeam team, Location spawn, Location corner1, Location corner2) {
        this.team = team;
        this.players = new ArrayList<>();
        this.spawn = spawn;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public boolean isTeamAlive() {
        if(dead) return true;
        ProfileCache cache = CastleWarsAPI.PROFILE_CACHE.get();
        return this.players.stream().anyMatch(p -> !cache.getProfile(p).getSpectator().isSpectating());
    }
}
