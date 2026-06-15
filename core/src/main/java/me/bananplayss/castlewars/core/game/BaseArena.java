package me.bananplayss.castlewars.core.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.team.Team;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.ArenaPrefab;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

@Getter
public class BaseArena {

    private String key;
    private String displayName;
    private String schematicName;

    /*
        Todo: every location INSIDE THE PREFAB
     */
    private Map<String, Team> teams;
    private Location spectator;

    public BaseArena(String key) {
    }

    public void reload() {
        FileConfiguration c = Main.getInstance();
    }
}
