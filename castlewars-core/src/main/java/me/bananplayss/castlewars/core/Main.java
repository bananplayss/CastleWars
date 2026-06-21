package me.bananplayss.castlewars.core;

import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.core.commands.MainCommand;
import me.bananplayss.castlewars.core.files.ConfigData;
import me.bananplayss.castlewars.core.files.FileManager;
import me.bananplayss.castlewars.core.arena.ArenaManagerImpl;
import me.bananplayss.castlewars.core.game.GameManager;
import me.bananplayss.castlewars.core.hooks.HookManager;
import me.bananplayss.castlewars.core.kits.KitManagerImpl;
import me.bananplayss.castlewars.core.listeners.PlayerInteractListener;
import me.bananplayss.castlewars.core.listeners.PlayerMoveListener;
import me.bananplayss.castlewars.core.map.managers.MapManager;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    @Getter private static Main instance;

    private FileManager fileManager;
    private ConfigData configData;

    private HookManager hookManager;
    private KitManagerImpl kitManager;
    private ArenaManagerImpl arenaManager;
    private ProfileCacheImpl profileCache;

    private MapManager mapManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;

        this.fileManager = new FileManager();
        this.configData = new ConfigData();

        this.hookManager = new HookManager();

        this.kitManager = new KitManagerImpl();

        this.arenaManager = new ArenaManagerImpl();
        this.fileManager.loadArenas();

        this.profileCache = new ProfileCacheImpl();
        CastleWarsAPI.setProfileCache(this.profileCache);
        
        this.mapManager = new MapManager();

        this.gameManager = new GameManager();

        MainCommand cmd = new MainCommand();
        cmd.registerMainCommand(this, "castlewars");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ProfileCacheImpl.getProfileImpl(onlinePlayer);
        }

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
