package me.bananplayss.castlewars.api.game;

import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.flags.GameFlagManager;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class Game {

    protected final BaseArena baseArena;

    protected Map<String, AbstractGameTeam> teams;

    protected GameFlagManager flagManager;

    protected Location spectatorSpawn;
    protected Location lobby;
    protected Location origin;

    public Game(BaseArena baseArena) {
        this.baseArena = baseArena;
        this.flagManager = new GameFlagManager();
    }

    public JoinResult join(Player player) {
        //Todo: if megy a game tesom akk return stb
        AbstractGameTeam team = getRandomTeam();
        if(team == null){
            return JoinResult.LOBBY_FULL;
        }

        //Todo: Gameplayer setupolni stb
        team.getPlayers().add(player);
        player.teleport(lobby);
        return JoinResult.SUCCESSFULLY;
    }

    public void leave(Player player) {
        AbstractGameTeam t = getTeam(player);
        if(t == null) return;
        t.getPlayers().remove(player);

        // ToDo: Teleport to spawn
    }

    public AbstractGameTeam getRandomTeam() {
        return teams.values().stream().filter(t -> t.getPlayers().size() < baseArena.getTeamSize()).findAny().orElse(null); // Átlátható be like:
    }

    public abstract void broadcast(Component component);

    @Nullable public abstract AbstractGameTeam getTeam(Player player);
}
