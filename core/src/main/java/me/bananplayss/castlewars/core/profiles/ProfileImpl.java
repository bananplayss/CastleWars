package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;

import java.util.UUID;

@Getter
public class ProfileImpl implements Profile {

    private final UUID uniqueId;

    private Game currentGame;
    private AbstractGameTeam team;

    public ProfileImpl(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
