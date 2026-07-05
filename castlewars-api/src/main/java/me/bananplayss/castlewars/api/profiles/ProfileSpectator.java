package me.bananplayss.castlewars.api.profiles;

import org.bukkit.entity.Player;

public interface ProfileSpectator {

    Profile getProfile();
    Player getPlayer();
    boolean isSpectating();
}
