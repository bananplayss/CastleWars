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
}