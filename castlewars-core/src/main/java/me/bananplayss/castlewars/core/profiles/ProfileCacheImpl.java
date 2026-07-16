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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ProfileCacheImpl implements ProfileCache {

    @Getter
    private final Map<UUID, Profile> profiles;

    public ProfileCacheImpl() {
        this.profiles = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isLoaded(UUID uuid) {
        return this.profiles.containsKey(uuid);
    }

    @Override
    public CompletableFuture<Profile> loadProfile(@NotNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            //            Main.getInstance().getProfileCache().getProfiles().put(uuid, prof);
//            prof.load();
            return Main.getInstance().getDatabase().loadProfile(uuid);
        });
    }

    @Override
    public CompletableFuture<Profile> loadProfile(@NotNull Player player) {
        return loadProfile(player.getUniqueId());
    }

    @Nullable
    public Profile getProfile(@NotNull Player player) {
        return getProfile(player.getUniqueId());
    }

    @Nullable
    public Profile getProfile(@NotNull UUID uuid) {
        if (this.profiles.containsKey(uuid)) {
            return this.profiles.get(uuid);
        }

        return null;
    }

//    // ez a function csak nekem van
//    @Nullable
//    public Profile getProfile(UUID uuid) {
//        if(this.profiles.containsKey(uuid)) {
//            return this.profiles.get(uuid);
//        }
//
//        if(load) {
//            ProfileImpl profile = new ProfileImpl(uuid);
//            this.profiles.put(uuid, profile);
//            profile.load();
//            return profile;
//        }
//        return null;
//    }

    public static ProfileImpl getProfileImpl(Player player) {
        return (ProfileImpl) Main.getInstance().getProfileCache().getProfile(player);
    }

    public static ProfileImpl getProfileImpl(UUID uuid) {
        return (ProfileImpl) Main.getInstance().getProfileCache().getProfile(uuid);
    }
}
