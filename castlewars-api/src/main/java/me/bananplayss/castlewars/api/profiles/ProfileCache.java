package me.bananplayss.castlewars.api.profiles;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface ProfileCache {

    Map<UUID, Profile> getProfiles();

    boolean isLoaded(UUID uuid);
    Profile getProfile(Player player);
    Profile getProfile(UUID uuid);
}
