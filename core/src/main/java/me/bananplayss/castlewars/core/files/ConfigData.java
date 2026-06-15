package me.bananplayss.castlewars.core.files;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.WorldGenerateType;

@Getter
public class ConfigData {

    private WorldGenerateType worldGenerateType;

    private int arenaCount;

    public ConfigData() {
        reload();
    }

    private void reload() {
        this.arenaCount = Main.getInstance().getFileManager().getConfig().getDefaultConfig().getOrSet("arena_count", 10, "Hany arena legyen generálvaxd");
    }
}
