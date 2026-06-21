package me.bananplayss.castlewars.api.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Game {

    private final int id;
    protected final BaseArena baseArena;

    protected Map<String, AbstractGameTeam> teams;

    protected Location spectatorSpawn;
    protected Location lobby;
    protected Location origin;

    public Game(int id, BaseArena baseArena) {
        this.id = id;
        this.baseArena = baseArena;
        this.teams = new HashMap<>();
    }

    public JoinResult join(Player player) {
        //Todo: if megy a game tesom akk return stb
        AbstractGameTeam team = getRandomTeam();
        if(team == null){
            return JoinResult.LOBBY_FULL;
        }

        System.out.println("10");
        Profile profile = CastleWarsAPI.getProfileCache().getProfile(player);
        profile.setCurrentGame(this);
        profile.setTeam(team);
        System.out.println("11 " + profile.getCurrentGame());

        System.out.println(player.getName() + " teamje: " + team.getTeam().getKey());
        //Todo: Gameplayer setupolni stb
        team.getPlayers().add(player);
        player.teleport(lobby);

        // Todo: Give kit
        return JoinResult.SUCCESS;
    }

    public void leave(Player player) {
        AbstractGameTeam t = getTeam(player);
        if(t == null) return;
        t.getPlayers().remove(player);

        Profile profile = CastleWarsAPI.getProfileCache().getProfile(player);
        profile.setCurrentGame(null);
        profile.setTeam(null);

        // ToDo: Teleport to spawn
    }

    public AbstractGameTeam getRandomTeam() {
        return teams.values().stream().filter(t -> t.getPlayers().size() < baseArena.getTeamSize()).findAny().orElse(null); // Átlátható be like:
    }

    public abstract void broadcast(Component component);

    @Nullable public abstract AbstractGameTeam getTeam(Player player);
}
