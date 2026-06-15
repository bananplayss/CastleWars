package me.bananplayss.castlewars.core.map.managers;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.ArenaPrefab;
import me.bananplayss.castlewars.core.map.WorldGenerateType;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseMapManager {

    private final WorldGenerateType type;

    private List<ArenaPrefab> arenaPrefabs;

    private List<String> arenaNames;


    public BaseMapManager(WorldGenerateType type) {
        this.type = type;
        this.arenaPrefabs = new ArrayList<>();
        //Todo: Find or get arenas
        File file = new File(Main.getInstance().getDataFolder(), "arenas" + File.separator + "desert_arena.schem");
        this.arenaPrefabs.add(new ArenaPrefab(file, "desert_arena"));

    }

    public abstract void generate();
}
