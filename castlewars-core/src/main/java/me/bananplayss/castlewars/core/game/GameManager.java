package me.bananplayss.castlewars.core.game;

import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.arena.BaseArenaImpl;
import me.bananplayss.castlewars.core.map.ArenaSchematic;
import org.bukkit.Bukkit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class GameManager {

    private final Map<Integer, Game> games;

    public GameManager() {
        this.games = new HashMap<>();

        mainLoop();
    }

    public Game KURVAANYAD() {
        return games.values().stream().toList().get(0);
    }

    public Game getGame(int id) {
        return games.get(id);
    }

    public Game getGame() {
        //Todo: Filter by gameType
        try {
            return games.values().stream().max(Comparator.comparingInt(g -> g.getAllPlayers().size())).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void removeGame(int id) {
        games.remove(id);
    }

    public Game createGame(BaseArenaImpl arena) {
        int id = games.size();
        switch (arena.getGameMode()) {
            case CAPTURE_THE_FLAG -> {
                ArenaSchematic schem = Main.getInstance().getMapManager().getManager().getArenaSchematicByName(arena.getSchematicName()); //Main.getInstance().getMapManager().getManager().getArenaPrefabs().get(0);
                FlagGameImpl fg = new FlagGameImpl(id, schem, arena);
                games.put(id, fg);
                return fg;
            }
        }

        return null;
    }

    public void mainLoop() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            for (Game value : this.games.values()) {
                value.getGameLoop().run();
            }
        }, 1L, 1L);
    }
}
