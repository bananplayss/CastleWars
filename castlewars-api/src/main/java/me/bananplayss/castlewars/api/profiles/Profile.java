package me.bananplayss.castlewars.api.profiles;

import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;

import java.util.UUID;

public interface Profile {

    UUID getUniqueId();

    Game getCurrentGame();
    AbstractGameTeam getTeam();

    void setCurrentGame(Game game);
    void setTeam(AbstractGameTeam team);
}
