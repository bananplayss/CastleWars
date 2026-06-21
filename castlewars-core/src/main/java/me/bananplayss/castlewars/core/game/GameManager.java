package me.bananplayss.castlewars.core.game;

import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.ArenaSchematic;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private final Map<Integer, Game> games;

    public GameManager() {
        this.games = new HashMap<>();
    }

    public void removeGame(int id) {
        games.remove(id);
    }

    public Game createGame(BaseArena arena) {
        int id = games.size();
        switch (arena.getGameMode()) {
            case CAPTURE_THE_FLAG -> {
                ArenaSchematic schem = Main.getInstance().getMapManager().getManager().getArenaPrefabs().get(0);
                FlagGameImpl fg = new FlagGameImpl(id, schem, arena);
                games.put(id, fg);
                return fg;
            }
        }

        return null;
    }
}
