package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.profiles.ProfileCache;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileCacheImpl implements ProfileCache {

    @Getter private final Map<UUID, Profile> profiles;

    public ProfileCacheImpl() {
        this.profiles = new HashMap<>();
    }

    @Override
    public boolean isLoaded(UUID uuid) {
        return this.profiles.containsKey(uuid);
    }

    @NotNull
    public Profile getProfile(Player player) {
        return getProfile(player.getUniqueId(), true);
    }

    @Nullable
    public Profile getProfile(UUID uuid) {
        if(this.profiles.containsKey(uuid)) {
            return this.profiles.get(uuid);
        }

        return null;
    }

    @Nullable
    public Profile getProfile(UUID uuid, boolean load) {
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

    public static ProfileImpl getProfileImpl(Player player) {
        return (ProfileImpl) Main.getInstance().getProfileCache().getProfile(player);
    }
}
