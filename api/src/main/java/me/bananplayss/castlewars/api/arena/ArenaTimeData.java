package me.bananplayss.castlewars.api.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class ArenaTimeData {

    private int waiting;
    private int starting;
    private int celebration;
    private int reset;

    public ArenaTimeData(KobaFile file) {
        this.file = file;

        reload();
    }

    public void reload() {
        FileConfiguration c = this.file.getConfig();
        this.waiting = c.getInt("waiting");
        this.starting = c.getInt("starting");
        this.celebration = c.getInt("celebration");
        this.reset = c.getInt("reset");
    }
}