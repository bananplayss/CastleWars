package me.bananplayss.castlewars.core;

import io.papermc.paper.event.player.PlayerServerFullCheckEvent;
import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.core.files.ConfigData;
import me.bananplayss.castlewars.core.files.FileManager;
import me.bananplayss.castlewars.core.hooks.HookManager;
import me.bananplayss.castlewars.core.map.managers.MapManager;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    @Getter private static Main instance;

    private FileManager fileManager;
    private ConfigData configData;

    private HookManager hookManager;
    private ProfileCacheImpl profileCache;

    private MapManager mapManager;

    @Override
    public void onEnable() {

        instance = this;

        this.fileManager = new FileManager();
        this.configData = new ConfigData();

        this.hookManager = new HookManager();

        this.profileCache = new ProfileCacheImpl();
        CastleWarsAPI.setProfileCache(this.profileCache);


        this.mapManager = new MapManager();

        //commandok vagy idk
        this.getCommand("stackcommand").setExecutor(new StackCommand());
    }

    @Override
    public void onDisable() {

    }
}
