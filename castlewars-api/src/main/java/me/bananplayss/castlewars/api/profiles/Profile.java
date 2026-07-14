package me.bananplayss.castlewars.api.profiles;

import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface Profile {

    UUID getUniqueId();

    ProfileSpectator getSpectator();
    Game getCurrentGame();
    AbstractGameTeam getTeam();

    void setCurrentGame(Game game);
    void setTeam(AbstractGameTeam team);
    ProfileStatistics getStatistics();
}
