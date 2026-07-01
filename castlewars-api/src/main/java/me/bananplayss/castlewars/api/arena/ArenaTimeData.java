package me.bananplayss.castlewars.api.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class ArenaTimeData {

    private long waiting;
    private long starting;
    private long celebration;
    private long reset;
}