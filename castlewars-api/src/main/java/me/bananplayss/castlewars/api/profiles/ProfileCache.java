package me.bananplayss.castlewars.api.profiles;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ProfileCache {

    Map<UUID, Profile> getProfiles();

    boolean isLoaded(UUID uuid);
    @NotNull CompletableFuture<@Nullable Profile> loadProfile(@NotNull UUID uuid);
    @NotNull CompletableFuture<@Nullable Profile> loadProfile(@NotNull Player player);

    @Nullable Profile getProfile(@NotNull Player player);
    @Nullable Profile getProfile(@NotNull UUID uuid);
}
