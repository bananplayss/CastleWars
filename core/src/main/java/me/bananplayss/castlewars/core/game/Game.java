package me.bananplayss.castlewars.core.game;

import me.bananplayss.castlewars.core.map.ArenaPrefab;

public class Game {

    private final ArenaPrefab map;
    private final BaseArena arenaConfig;

    public Game(ArenaPrefab map, BaseArena arenaConfig) {
        this.map = map;
        this.arenaConfig = arenaConfig;
    }
}
