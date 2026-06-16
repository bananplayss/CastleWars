package me.bananplayss.castlewars.core.arena;

import lombok.Getter;
import me.bananplayss.castlewars.api.arena.ArenaManager;
import me.bananplayss.castlewars.api.arena.BaseArena;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ArenaManagerImpl implements ArenaManager {
    private final Map<String, BaseArena> arenas;

    public ArenaManagerImpl(){
        this.arenas = new HashMap<>();
    }

    public BaseArena getArena(String name) {
        return this.arenas.get(name);
    }

    public BaseArenaImpl getArenaImpl(String name) {
        return (BaseArenaImpl) this.arenas.get(name);
    }

    @Override
    public void registerArena(BaseArena arena) {
        this.arenas.put(arena.getName(), arena);
        System.out.println("Registered arena: " + arena.getName());
    }

    @Override
    public void unregisterArena(BaseArena arena) {
        this.arenas.remove(arena.getName());
    }
}
