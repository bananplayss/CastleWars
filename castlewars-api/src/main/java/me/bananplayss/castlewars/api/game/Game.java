package me.bananplayss.castlewars.api.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.action.GameActionManager;
import me.bananplayss.castlewars.api.game.phases.GamePhaseManager;
import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Game {

    private final int id;
    protected final BaseArena baseArena;

    protected Map<String, AbstractGameTeam> teams;

    protected Location spectatorSpawn;
    protected Location lobby;
    protected Location origin;

    @Setter
    protected String kitName;

    protected GamePhaseManager phaseManager;
    protected GameActionManager actionManager;

    protected Runnable gameLoop;

    public Game(int id, BaseArena baseArena) {
        this.id = id;
        this.baseArena = baseArena;
        this.teams = new HashMap<>();
        this.actionManager = new GameActionManager(this);
        this.kitName = baseArena.getKitName();
    }

    public JoinResult join(Player player) {
        //Todo: if megy a game tesom akk return stb
        AbstractGameTeam team = getRandomTeam();
        if(team == null){
            return JoinResult.LOBBY_FULL;
        }

        Profile profile = CastleWarsAPI.PROFILE_CACHE.get().getProfile(player);
        profile.setCurrentGame(this);
        profile.setTeam(team);

        //Todo: Gameplayer setupolni stb
        team.getPlayers().add(player);
        player.teleport(this.lobby);

        // reset player
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGlowing(false);
        // vége

        this.joinPlayer(player);

        // Todo: Give kit
        return JoinResult.SUCCESS;
    }

    public void leave(Player player) {
        AbstractGameTeam t = getTeam(player);
        if(t == null) return;
        t.getPlayers().remove(player);

        Profile profile = CastleWarsAPI.PROFILE_CACHE.get().getProfile(player);
        profile.setCurrentGame(null);
        profile.setTeam(null);

        // reset player
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGlowing(false);
        // vége

        CastleWarsAPI.RESPAWN_MANAGER.get().removeRespawn(player);

        this.leavePlayer(player);

        // ToDo: Teleport to spawn
    }

    public AbstractGameTeam getRandomTeam() {
        return teams.values().stream().min(Comparator.comparingInt(t -> t.getPlayers().size())).orElse(null);
        //return teams.values().stream().filter(t -> t.getPlayers().size() < baseArena.getTeamSize()).findAny().orElse(null); // Átlátható be like:
    }
    
    public Kit getKit() {
        return CastleWarsAPI.KIT_MANAGER.get().getKit(kitName);
    }

    public abstract void broadcast(Component component);

    @Nullable public abstract AbstractGameTeam getTeam(Player player);

    public List<Player> getAllPlayers() {
        return this.teams.values().stream().flatMap(t -> t.getPlayers().stream()).toList();
    }

    public abstract void joinPlayer(Player player);
    public abstract void leavePlayer(Player player);

    public abstract void start();
    public abstract void reset();
//    public abstract void reset();
}
