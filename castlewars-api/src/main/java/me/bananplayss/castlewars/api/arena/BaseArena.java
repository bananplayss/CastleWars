package me.bananplayss.castlewars.api.arena;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.GameMode;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;
import org.bukkit.World;

import java.util.Map;

@Getter
public class BaseArena {

    protected boolean enabled;
    protected String name;
    protected World world;
    protected String displayName;
    protected String schematicName;
    protected VectorLocation spectatorVector;
    protected VectorLocation lobbyVector;
    protected int respawnDelay;
    protected int scoreLimit;
    protected int teamSize;
    protected int maxPlayerCount;

    protected Vector3i corner1;
    protected Vector3i corner2;

    protected String kitName;

    protected ArenaTimeData timeData;

    protected GameMode gameMode;
    protected Map<String, AbstractTeam> teams;

    public void reload() {}
}
