package me.bananplayss.castlewars.core;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.core.commands.MainCommand;
import me.bananplayss.castlewars.core.database.FileDatabase;
import me.bananplayss.castlewars.core.digging.BannerDiggingManager;
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
import me.bananplayss.castlewars.core.visibility.FakeNameTagManager;
import me.bananplayss.castlewars.core.visibility.MovePacketListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
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
    private BannerDiggingManager bannerDiggingManager;

    private IDatabase database;

    private PacketListenerCommon movePacketListenerCommon;
    private PacketListenerCommon bannerDiggingManagerListenerCommon;

    @Override
    public void onLoad() {
        this.bannerDiggingManager = new BannerDiggingManager();
        //this.movePacketListener = new MovePacketListener();

        EventManager events = PacketEvents.getAPI().getEventManager();
        this.movePacketListenerCommon = events.registerListener(new MovePacketListener(), PacketListenerPriority.NORMAL);
        this.bannerDiggingManagerListenerCommon = events.registerListener(this.bannerDiggingManager, PacketListenerPriority.NORMAL);
    }

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

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPostRespawnListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerDismountListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new SpectateMoveListener(), this);

        ArmorEquipEvent.registerListener(this);
        pm.registerEvents(new ArmorEquipListener(), this);
    }

    @Override
    public void onDisable() {
        EventManager events = PacketEvents.getAPI().getEventManager();
        events.unregisterListener(this.movePacketListenerCommon);
        events.unregisterListener(this.bannerDiggingManagerListenerCommon);
    }
}
