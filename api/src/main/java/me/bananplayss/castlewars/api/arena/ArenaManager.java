package me.bananplayss.castlewars.api.arena;

import java.util.Map;

public interface ArenaManager {
    void registerArena(BaseArena arena);
    void unregisterArena(BaseArena arena);
    Map<String, BaseArena> getArenas();

    BaseArena getArena(String name);
}
