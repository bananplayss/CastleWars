package me.bananplayss.castlewars.core.map.managers;

import me.bananplayss.castlewars.core.map.ArenaPrefab;
import me.bananplayss.castlewars.core.map.WorldGenerateType;
import org.bukkit.Location;


import java.util.HashMap;

public class WorldEditMapManager extends BaseMapManager {

    private HashMap<Location,ArenaPrefab> builtMaps;

    private final int 

    public WorldEditMapManager() {
        super(WorldGenerateType.WORLDEDIT);
        this.builtMaps = new HashMap<>();
    }

    @Override
    public void generate() {

    }
}
