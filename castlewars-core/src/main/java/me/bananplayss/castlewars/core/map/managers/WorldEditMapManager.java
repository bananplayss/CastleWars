package me.bananplayss.castlewars.core.map.managers;

import lombok.Getter;
import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.ArenaSchematic;
import me.bananplayss.castlewars.core.map.WorldGenerateType;
import org.bukkit.Bukkit;
import org.bukkit.Location;


import java.util.HashMap;
import java.util.Map;

@Getter
public class WorldEditMapManager extends BaseMapManager {

    private final Map<Location, ArenaSchematic> builtMaps;

    private final int distanceBetweenSpawnPoints = 1000;

    public WorldEditMapManager() {
        super(WorldGenerateType.WORLDEDIT);
        this.builtMaps = new HashMap<>();
    }

    @Override
    public void generate() {
        System.out.println("Starting map generation!");
        int index = 0;
        int c = Main.getInstance().getConfigData().getArenaCount();

        for (ArenaSchematic arenaPrefab : this.getArenaPrefabs()) {
            for (int i = 0; i < c; i++) {
                Location spawn = new Location(Bukkit.getWorld("castlewars"),0,100,0).add((index % 2) * distanceBetweenSpawnPoints , 0, index * distanceBetweenSpawnPoints);
                if(builtMaps.containsKey(spawn)){
                    System.out.println("Map already built: " + spawn);
                    continue;
                }

                System.out.println("Building map: " + arenaPrefab.getName() + "To: " + spawn);
                arenaPrefab.buildTo(spawn,() -> {
                    builtMaps.put(spawn, arenaPrefab);
                    //Todo: lelassítani ha gatya a helyzet, pl Lockkal, vagy count mennyi db build van folyamatban
                } );
                index++;
            }
        }
    }
}
