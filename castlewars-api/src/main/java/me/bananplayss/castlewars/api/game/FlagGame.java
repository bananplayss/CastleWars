package me.bananplayss.castlewars.api.game;

import me.bananplayss.castlewars.api.game.flags.GameFlagManager;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import org.bukkit.Location;

public interface FlagGame {

    GameFlagManager getFlagManager();
    void placeBanner(GameFlagTeam team, Location location);
}
