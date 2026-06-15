package me.bananplayss.castlewars.core.map.managers;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.WorldGenerateType;

@Getter
public class MapManager {

    private BaseMapManager manager;

    public MapManager(BaseMapManager manager) {
        // Nem használok ConfigData-t itt mert reload ugyse hat rá. Fix resi kell
        WorldGenerateType type = WorldGenerateType.valueOf(
                Main.getInstance().getFileManager().getConfig().getDefaultConfig().getOrSet("world_generate_type", "WORLDEDIT", "Possible types: WORLDEDIT, SLIME_WORLD")
        );
        this.manager = manager;

    }
}
