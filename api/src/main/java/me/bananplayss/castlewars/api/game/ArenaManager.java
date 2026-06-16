package me.bananplayss.castlewars.api.game;

import java.util.Map;

public interface ArenaManager {
    void registerArena();
    void unregisterArena();
    Map<String, Object> getArenas();
}
