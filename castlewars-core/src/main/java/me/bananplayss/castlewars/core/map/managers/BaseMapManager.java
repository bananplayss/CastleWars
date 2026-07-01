package me.bananplayss.castlewars.core.map.managers;

import lombok.Getter;
import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.arena.BaseArenaImpl;
import me.bananplayss.castlewars.core.map.ArenaSchematic;
import me.bananplayss.castlewars.core.map.WorldGenerateType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseMapManager {

    private final WorldGenerateType type;

    private final List<ArenaSchematic> arenaPrefabs;

    public BaseMapManager(WorldGenerateType type) {
        this.type = type;
        this.arenaPrefabs = new ArrayList<>();
        //Todo: Find or get arenas



        File f = new File(Main.getInstance().getDataFolder(), "schematics");
        if (!f.exists()) f.mkdir();
        for (BaseArena arena : Main.getInstance().getArenaManager().getArenas().values()) {
            if(arenaPrefabs.stream().filter(a -> a.getName().equalsIgnoreCase(arena.getSchematicName())).findFirst().orElse(null) != null) continue;
            File schematic = new File(Main.getInstance().getDataFolder(), "schematics" + File.separator + arena.getSchematicName() + ".schem");
            this.arenaPrefabs.add(new ArenaSchematic(schematic, arena.getSchematicName()));

        }
    }

    public ArenaSchematic getArenaSchematicByName(String name) {
        return arenaPrefabs.stream().filter(arenaSchematic -> arenaSchematic.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public abstract void generate();
}
