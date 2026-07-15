package me.bananplayss.castlewars.core;

import com.github.retrooper.packetevents.PacketEvents;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.core.commands.MainCommand;
import me.bananplayss.castlewars.core.database.FileDatabase;
import me.bananplayss.castlewars.core.effects.EffectManagerImpl;
import me.bananplayss.castlewars.core.files.ConfigData;
import me.bananplayss.castlewars.core.files.FileManager;
import me.bananplayss.castlewars.core.arena.ArenaManagerImpl;
import me.bananplayss.castlewars.core.game.GameManager;
import me.bananplayss.castlewars.core.hooks.HookManager;
import me.bananplayss.castlewars.core.kits.KitManagerImpl;
import me.bananplayss.castlewars.core.database.IDatabase;
import me.bananplayss.castlewars.core.listeners.*;
import me.bananplayss.castlewars.core.map.managers.MapManager;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.scoreboard.ScoreboardManagerImpl;
import me.bananplayss.castlewars.core.utils.RespawnManagerImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    public static boolean PACKET_EVENTS = true;

    @Getter private static Main instance;

    private FileManager fileManager;
    private ConfigData configData;

    private HookManager hookManager;
    private KitManagerImpl kitManager;
    private ArenaManagerImpl arenaManager;
    private ProfileCacheImpl profileCache;

    private ScoreboardManagerImpl scoreboardManager;

    private MapManager mapManager;
    private GameManager gameManager;

    private RespawnManagerImpl respawnManager;

    private EffectManagerImpl effectManager;

    private IDatabase database;

    @Override
    public void onEnable() {
        instance = this;
        this.fileManager = new FileManager();
        this.configData = new ConfigData();

        this.database = new FileDatabase();

        this.hookManager = new HookManager();

        this.kitManager = new KitManagerImpl();
        CastleWarsAPI.KIT_MANAGER.set(this.kitManager);

        this.arenaManager = new ArenaManagerImpl();
        this.fileManager.loadArenas();

        this.profileCache = new ProfileCacheImpl();
        CastleWarsAPI.PROFILE_CACHE.set(this.profileCache);
        
        this.mapManager = new MapManager();
        this.gameManager = new GameManager();

        this.scoreboardManager = new ScoreboardManagerImpl();

        this.respawnManager = new RespawnManagerImpl();
        CastleWarsAPI.RESPAWN_MANAGER.set(this.respawnManager);

        this.effectManager = new EffectManagerImpl();
        CastleWarsAPI.EFFECT_MANAGER.set(this.effectManager);

        MainCommand cmd = new MainCommand();
        cmd.registerMainCommand(this, "castlewars");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.profileCache.loadProfile(onlinePlayer);
        }

        getServer().getPluginManager().registerEvents(new PlayerPostRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDismountListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        ArmorEquipEvent.registerListener(this);
        getServer().getPluginManager().registerEvents(new ArmorEquipListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
