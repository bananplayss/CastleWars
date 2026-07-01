package me.bananplayss.castlewars.core.profiles;

import fr.mrmicky.fastboard.adventure.FastBoard;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class ProfileImpl implements Profile {

    private final UUID uniqueId;

    private Game currentGame;
    private AbstractGameTeam team;

    private FastBoard scoreboard;

    public ProfileImpl(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

    @Override
    public void setTeam(AbstractGameTeam team) {
        this.team = team;
    }
}
