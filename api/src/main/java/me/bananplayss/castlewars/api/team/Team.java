package me.bananplayss.castlewars.api.team;

import org.bukkit.Location;

public interface Team {

    String getName();
    String getColor();
    int getMaxPlayers();
    Location getLocation();
    Location getFlagLocation(); // Buzi vagy, remelem minden teamhez jár Flag location
}
