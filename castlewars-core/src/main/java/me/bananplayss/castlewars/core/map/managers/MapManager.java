package me.bananplayss.castlewars.core.map.managers;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.WorldGenerateType;

@Getter
public class MapManager {

    private final BaseMapManager manager;

    public MapManager() {
        // Nem használok ConfigData-t itt mert reload ugyse hat rá. Fix resi kell      ok tesó
        WorldGenerateType type = WorldGenerateType.valueOf(
                Main.getInstance().getFileManager().getConfig().getDefaultConfig().getOrSet("world_generate_type", "WORLDEDIT", "Possible types: WORLDEDIT, SLIME_WORLD")
        );

        switch (type) {
            case WORLDEDIT -> this.manager = new WorldEditMapManager();
            //case SLIME_WORLD -> this.manager = new WorldEditMapManager();
            default -> this.manager = new WorldEditMapManager();
        }
        this.manager.generate();
    }

}
