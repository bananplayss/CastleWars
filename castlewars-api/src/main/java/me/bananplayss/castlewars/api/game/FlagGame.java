package me.bananplayss.castlewars.api.game;

import me.bananplayss.castlewars.api.game.flags.GameFlagManager;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.Map;

public interface FlagGame {

    Game getGame();
    GameFlagManager getFlagManager();
    void placeBanner(GameFlagTeam team, Location location, BlockFace rotation);

    void destroyFlags();

    long getFlagProtection();
    void setFlagProtection(long time);
    void setRespawnEnabled(boolean enabled);
    boolean isRespawnEnabled();
}
