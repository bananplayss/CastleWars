package me.bananplayss.castlewars.core.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.ArenaManager;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ArenaManagerImpl implements ArenaManager {
    private final Map<String,Object> arenas;
    public ArenaManagerImpl(){
        this.arenas = new HashMap<>();
    }

    @Override
    public void registerArena() {

    }

    @Override
    public void unregisterArena() {

    }
}
