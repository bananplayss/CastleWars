package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.profiles.ProfileCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileCacheImpl implements ProfileCache {

    private final Map<UUID, ProfileImpl> profiles;

    public ProfileCacheImpl() {
        this.profiles = new HashMap<>();
    }

    @Override
    public boolean isLoaded(UUID uuid) {
        return this.profiles.containsKey(uuid);
    }

    @NotNull
    public ProfileImpl getProfile(Player player) {
        if(this.profiles.containsKey(player.getUniqueId())) {
            return this.profiles.get(player.getUniqueId());
        }

        return new ProfileImpl(player.getUniqueId());
    }

    @Nullable
    public ProfileImpl getProfile(UUID uuid) {
        if(this.profiles.containsKey(uuid)) {
            return this.profiles.get(uuid);
        }

        return null;
    }

    @Nullable
    public ProfileImpl getProfile(UUID uuid, boolean load) {
        if(this.profiles.containsKey(uuid)) {
            return this.profiles.get(uuid);
        }
        if(load) {
            ProfileImpl profile = new ProfileImpl(uuid);
            this.profiles.put(uuid, profile);
            return profile;
        }
        return null;
    }
}
