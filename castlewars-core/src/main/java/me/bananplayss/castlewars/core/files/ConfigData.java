package me.bananplayss.castlewars.core.files;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.WorldGenerateType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
public class ConfigData {

    private final ScoreboardData scoreboardData;

    private Location spawn;
    private WorldGenerateType worldGenerateType;
    private int arenaCount;
    private long respawnTime;


    public ConfigData() {
        this.scoreboardData = new ScoreboardData();
        reload();
    }

    public void reload() {
        this.arenaCount = Main.getInstance().getFileManager().getConfig().getDefaultConfig().getOrSet("arena_count", 10, "Hany arena legyen generálvaxd");
        this.respawnTime = Main.getInstance().getFileManager().getConfig().getDefaultConfig().getOrSet("respawn_time", 5) * 1000L;
        this.spawn = Main.getInstance().getFileManager().getConfig().getDefaultConfig().getOrSet("spawn",
                new Location(Bukkit.getWorlds().get(0), 0.5, 0, 0.5, 0, 0), false, true);
        this.scoreboardData.reload();
    }
}
