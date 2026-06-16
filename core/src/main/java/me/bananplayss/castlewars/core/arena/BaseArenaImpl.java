package me.bananplayss.castlewars.core.arena;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.Getter;
import me.bananplayss.castlewars.api.arena.ArenaTimeData;
import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.GameMode;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import me.bananplayss.castlewars.api.teams.FlagTeam;
import me.bananplayss.castlewars.api.teams.NexusTeam;
import me.bananplayss.castlewars.api.teams.ZoneTeam;
import me.bananplayss.castlewars.api.utils.BoundingBox;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.game.FlagGame;
import me.bananplayss.castlewars.core.kobalib.KobaFile;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class BaseArenaImpl extends BaseArena {
    private final KobaFile file;

    public BaseArenaImpl(KobaFile file) {
        this.file = file;

        this.teams = new HashMap<>();

        reload();
    }

    @Override
    public void reload() {
        FileConfiguration c = this.file.getConfig();

        this.enabled = c.getBoolean("enabled");
        this.name = c.getString("name");
        this.world = Bukkit.getWorld(c.getString("world"));
        if (this.world == null) {
            Bukkit.getLogger().severe("The world " + c.getString("world") + " does not exist!");
            return;
        }

        this.mode = GameMode.valueOf(c.getString("mode").toUpperCase());
        this.displayName = c.getString("display_name");
        this.schematicName = c.getString("schematic");
        this.spectatorVector = VectorLocation.fromString(c.getString("spectator"));
        this.lobbyVector = VectorLocation.fromString(c.getString("lobby"));

        boolean respawnEnabled = c.getBoolean("respawn.enabled");
        int respawnDelay = c.getInt("respawn.delay");
        this.respawnDelay = respawnEnabled ? respawnDelay : -1;

        this.scoreLimit = c.getInt("score.limit");

        this.timeData = new ArenaTimeData(
                c.getInt("waiting"),
                c.getInt("starting"),
                c.getInt("celebration"),
                c.getInt("reset")
        );

        int maxCount = c.getInt("teams.count");
        this.teamSize = maxCount;
        ConfigurationSection section = c.getConfigurationSection("teams");

        for (String key : section.getKeys(false)) {
            if (key.equalsIgnoreCase("count")) continue;

            String color = section.getString(key + ".color");
            String dpName = section.getString(key + ".display_name");
            VectorLocation spawn = VectorLocation.fromString(section.getString(key + ".spawn"));

            BoundingBox boundingBox = new BoundingBox(
                    Vector3i.fromString(section.getString(key + ".corners.1")),
                    Vector3i.fromString(section.getString(key + ".corners.2"))
            );

            switch (this.mode) {
                case CAPTURE_THE_FLAG -> {
                    Vector3i flagVector = Vector3i.fromString(section.getString(key + ".flag.location"));
                    BlockFace rotation = BlockFace.valueOf(section.getString(key + ".flag.rotation").toUpperCase());

                    DyeColor baseColor = DyeColor.valueOf(section.getString(key + ".flag.banner.base_color").toUpperCase());

                    List<Pattern> patterns = new ArrayList<>();
                    for (Map<?, ?> map : section.getMapList(key + ".flag.banner.patterns")) {
                        String patColorS = (String) map.get("color");
                        DyeColor patColor = DyeColor.valueOf(patColorS.toUpperCase());
                        PatternType patType = RegistryAccess.registryAccess().getRegistry(RegistryKey.BANNER_PATTERN)
                                .get(net.kyori.adventure.key.Key.key("minecraft:" + map.get("pattern")));
                        if (patType == null) {
                            System.out.println("Null a patType");
                        }

                        patterns.add(new Pattern(patColor, patType));
                    }


                    ItemStack banner = new ItemStack(Material.WHITE_BANNER);
                    BannerMeta meta = (BannerMeta) banner.getItemMeta();
                    patterns.forEach(meta::addPattern);

                    meta.getPersistentDataContainer().set(FlagGame.FLAG_TEAM_KEY, PersistentDataType.STRING, key);
                    banner.setItemMeta(meta);

                    FlagTeam t = new FlagTeam(key, dpName, color, spawn, boundingBox, flagVector, rotation, baseColor, patterns, banner);
                    this.teams.put(key, t);
                }

                case CAPTURE_THE_ZONE -> {
                    Vector3i zone = Vector3i.fromString(section.getString(key + ".zone.location"));
                    int radius = section.getInt(key + ".zone.radius");
                    ZoneTeam t = new ZoneTeam(key, dpName, color, spawn, boundingBox, zone, radius);
                    this.teams.put(key, t);
                }

                case BREAK_THE_NEXUS -> {
                    Vector3i block = Vector3i.fromString(section.getString(key + ".nexus.location"));
                    NexusTeam t = new NexusTeam(key, dpName, color, boundingBox, spawn, block);
                    this.teams.put(key, t);
                }
            }
        }
    }
}
