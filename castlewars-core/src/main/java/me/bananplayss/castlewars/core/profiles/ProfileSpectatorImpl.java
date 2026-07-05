package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.profiles.ProfileSpectator;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.entity.Player;

@Getter
public class ProfileSpectatorImpl implements ProfileSpectator {

    private final Profile profile;
    @Setter
    private Player player;

    private boolean spectating;

    public ProfileSpectatorImpl(Profile profile) {
        this.profile = profile;
        this.spectating = false;
    }

    public void setSpectator() {
        if (this.player == null) return;
        for (Player allPlayer : this.profile.getCurrentGame().getAllPlayers()) {
            allPlayer.hidePlayer(Main.getInstance(), this.player);
        }
        this.player.sendMessage("Spectator vagy");
        this.spectating = true;
    }

    public void removeSpectator() {
        if (this.player == null) return;
        for (Player allPlayer : this.profile.getCurrentGame().getAllPlayers()) {
            allPlayer.hidePlayer(Main.getInstance(), this.player);
        }
        this.player.sendMessage("Most már NEM vagy spectator");
        this.spectating = false;
    }
}
