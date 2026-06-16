package me.bananplayss.castlewars.core.map.managers;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.ArenaSchematic;
import me.bananplayss.castlewars.core.map.WorldGenerateType;
import org.bukkit.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public abstract class BaseMapManager {

    private final WorldGenerateType type;

    private List<ArenaSchematic> arenaPrefabs;




    public BaseMapManager(WorldGenerateType type) {
        this.type = type;
        this.arenaPrefabs = new ArrayList<>();
        //Todo: Find or get arenas
        File file = new File(Main.getInstance().getDataFolder(), "arenas" + File.separator + "desert_arena.schem");
        this.arenaPrefabs.add(new ArenaSchematic(file, "desert_arena"));

    }

    public abstract void generate();
}
