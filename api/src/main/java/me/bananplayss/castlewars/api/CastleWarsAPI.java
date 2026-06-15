package me.bananplayss.castlewars.api;

import lombok.Getter;
import me.bananplayss.castlewars.api.profiles.ProfileCache;
import org.bukkit.plugin.java.JavaPlugin;

public class CastleWarsAPI extends JavaPlugin {

    private static ProfileCache profileCache;

    public static ProfileCache getProfileCache() {
        if (profileCache == null) {
            throw new IllegalStateException("PlayerCache not initialized yet!");
        }
        return profileCache;
    }
    public static void setProfileCache(ProfileCache cache) {
        if (profileCache != null) {
            throw new IllegalStateException("PlayerCache already set!");
        }
        profileCache = cache;
    }
}
