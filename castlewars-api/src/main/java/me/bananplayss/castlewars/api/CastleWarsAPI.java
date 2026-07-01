package me.bananplayss.castlewars.api;

import me.bananplayss.castlewars.api.effects.EffectManager;
import me.bananplayss.castlewars.api.kits.KitManager;
import me.bananplayss.castlewars.api.profiles.ProfileCache;
import me.bananplayss.castlewars.api.utils.RespawnManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CastleWarsAPI extends JavaPlugin {

    public static final Singleton<ProfileCache> PROFILE_CACHE = new Singleton<>("ProfileCache");
    public static final Singleton<RespawnManager> RESPAWN_MANAGER = new Singleton<>("RespawnManager");
    public static final Singleton<KitManager> KIT_MANAGER = new Singleton<>("KitManager");
    public static final Singleton<EffectManager> EFFECT_MANAGER = new Singleton<>("EffectManager");
}
