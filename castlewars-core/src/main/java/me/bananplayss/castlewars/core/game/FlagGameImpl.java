package me.bananplayss.castlewars.core.game;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.action.BannerGoneAction;
import me.bananplayss.castlewars.api.game.action.KitUpgradeAction;
import me.bananplayss.castlewars.api.game.flags.GameFlagManager;
import me.bananplayss.castlewars.api.game.phases.CelebrationPhase;
import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.game.phases.StartingPhase;
import me.bananplayss.castlewars.api.game.phases.WaitingPhase;
import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import me.bananplayss.castlewars.api.teams.FlagTeam;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.arena.BaseArenaImpl;
import me.bananplayss.castlewars.core.effects.RingOuterEffect;
import me.bananplayss.castlewars.core.game.phases.GamePhaseManagerImpl;
import me.bananplayss.castlewars.core.map.ArenaSchematic;
import me.bananplayss.castlewars.core.map.managers.WorldEditMapManager;
import me.bananplayss.castlewars.core.utils.TeamColorConverter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
public class FlagGameImpl extends Game implements FlagGame {
    public final static NamespacedKey FLAG_TEAM_KEY = new NamespacedKey(Main.getInstance(), "team");

    private final ArenaSchematic map;
    private final GameFlagManager flagManager;
    private Location origin;

    @Setter
    private long flagProtection;

//    private Map<String, GameFlagTeam> gameTeams;
    //AbsLoc = GameArenaCenter + (RelativePos - PrefabOrigin)


    public FlagGameImpl(int id, ArenaSchematic map, BaseArenaImpl arenaConfig) {
        super(id, arenaConfig);
        this.map = map;
        this.flagManager = new GameFlagManager(this);
        this.phaseManager = new GamePhaseManagerImpl();
        this.gameLoop = new GameLoop(this);

        this.flagProtection = arenaConfig.getFile().getConfig().getInt("flag_protection", 5) * 1000L;

        for (Map.Entry<Location, ArenaSchematic> entry : ((WorldEditMapManager) Main.getInstance().getMapManager().getManager()).getBuiltMaps().entrySet()) {
            if (entry.getValue().equals(this.map)) {
                this.origin = entry.getKey();
                break;
            }
        }

        if (this.origin == null) {
            Bukkit.getLogger().severe("No built  " + this.map.getName() + " arena is found!");
            return;
        }

        ((WorldEditMapManager) Main.getInstance().getMapManager().getManager()).getBuiltMaps().remove(this.origin);

        System.out.println("ORIGIN: " + origin);
        // load teams
        for (Map.Entry<String, AbstractTeam> baseTeams : arenaConfig.getTeams().entrySet()) {
            GameFlagTeam t = new GameFlagTeam(
                    relLocationToAbsolute(arenaConfig.getTeams().get(baseTeams.getKey()).getSpawn()),
//                    BoundingBox.of(
//                            relLocationToAbsolute(baseTeams.getValue().getBoundingBox().getBound1()),
//                            relLocationToAbsolute(baseTeams.getValue().getBoundingBox().getBound2())
//                    ),
                    (FlagTeam) baseTeams.getValue(),
                    relLocationToAbsolute(baseTeams.getValue().getCorner1()),
                    relLocationToAbsolute(baseTeams.getValue().getCorner2())
            );

            Location flagSpawnLoc = relLocationToAbsolute(((FlagTeam) baseTeams.getValue()).getFlagVector());
            t.setFlagSpawn(flagSpawnLoc);
            RingOuterEffect e = new RingOuterEffect(TeamColorConverter.parseColorOrThrow(t.getTeam().getColor()), 500, 2, null);
            e.setLooping(true);
            t.setRingEffect(e);
            this.teams.put(baseTeams.getKey(), t);
        }

        this.spectatorSpawn = relLocationToAbsolute(arenaConfig.getSpectatorVector());
//        System.out.println("SPAWN: " + arenaConfig.getSpectatorVector() + "     " + spectatorSpawn);
        this.lobby = relLocationToAbsolute(arenaConfig.getLobbyVector());
        placeBanners();

//        ConfigurationSection actions = arenaConfig.getFile().getConfig().getConfigurationSection("actions");
        for (Map<?, ?> action : arenaConfig.getFile().getConfig().getMapList("actions")) {
            String type = (String) action.get("type");
            String dpName = (String) action.get("display_name");
            long delay = (int) action.get("delay") * 1000L;
            switch (type.toLowerCase()) {
                case "kit_upgrade":
                    String kitName = (String) action.get("kit");
                    this.actionManager.getActions().add(new KitUpgradeAction(dpName, kitName, delay));
                    break;
                case "banner_gone":
                    this.actionManager.getActions().add(new BannerGoneAction(dpName, delay));
                    break;
            }
        }

        this.phaseManager.getPhases().add(new WaitingPhase(this, arenaConfig.getTimeData().getWaiting()));
        this.phaseManager.getPhases().add(new StartingPhase(this, arenaConfig.getTimeData().getStarting()));
        this.phaseManager.getPhases().add(new RunningPhase(this, arenaConfig.getTimeData().getReset()));
        this.phaseManager.getPhases().add(new CelebrationPhase(this, arenaConfig.getTimeData().getCelebration()));
    }

    public void placeBanners() {
        for (AbstractGameTeam value : this.teams.values()) {
            GameFlagTeam team = (GameFlagTeam) value;
            placeBanner(team, team.getFlagSpawn(), null);

        }
    }

    // mar megirva sima game-be
//    public AbstractGameTeam getRandomTeam() {
//        return teams.values().stream().filter(t -> t.getPlayers().size() < arenaConfig.getTeamSize()).findAny().orElse(null); // Átlátható be like:
//    }

    @Nullable
    public GameFlagTeam getTeam(Player player) {
        for (AbstractGameTeam value : this.teams.values()) {
            if (value.getPlayers().contains(player))
                return (GameFlagTeam) value;
        }
        return null;
    }

    @Override
    public void joinPlayer(Player player) {
        Main.getInstance().getScoreboardManager().show(player, this);
    }

    @Override
    public void leavePlayer(Player player) {
        Main.getInstance().getScoreboardManager().delete(player);
    }

    @Override
    public void start() {
        Kit startKit = Main.getInstance().getKitManager().getKit(this.kitName);
        for (AbstractGameTeam value : this.teams.values()) {
            value.getPlayers().forEach(p -> {
                startKit.give(p);
                p.teleport(value.getSpawn());
            });
        }

        System.out.println("Game indult");
    }

    @Override
    public void reset() {
        Location spawn = Main.getInstance().getConfigData().getSpawn();
        this.getAllPlayers().forEach(p -> p.teleport(spawn));
    }

    @Override
    public void rejoin(Player player) {

    }

    @Override
    public void giveKitAll() {
        Kit kit = Main.getInstance().getKitManager().getKit(this.kitName);
        for (Player allPlayer : this.getAllPlayers()) {
            kit.give(allPlayer);
        }
    }

    public void broadcast(Component component) {
        for (AbstractGameTeam value : this.teams.values()) {
            for (Player player : value.getPlayers()) {
                player.sendMessage(component);
            }
        }
    }

    private Location relLocationToAbsolute(Vector3i rel) {
        return new Location(
                origin.getWorld(),
                origin.getBlockX() + rel.getX(),
                origin.getBlockY() + rel.getY(),
                origin.getBlockZ() + rel.getZ()
        );
    }

    private Location relLocationToAbsolute(VectorLocation rel) {
        return new Location(
                origin.getWorld(),
                origin.getBlockX() + rel.getX(),
                origin.getBlockY() + rel.getY(),
                origin.getBlockZ() + rel.getZ(),
                rel.getYaw(),
                rel.getPitch()
        );
    }


//    private Location relLocationToAbsolute(VectorLocation relativeLocation) {
//        Vector3i delta = new Vector3i((int) (relativeLocation.getX() - map.getOrigin().getBlockX()), (int) (relativeLocation.getY() - map.getOrigin().getBlockY()), (int) (relativeLocation.getZ() - map.getOrigin().getBlockZ()));
//        return new Location(origin.getWorld(), origin.getBlockX() + delta.getX(), origin.getBlockY() + delta.getY(), origin.getBlockZ() + delta.getZ());
//    }
//
//    private Location relLocationToAbsolute(Vector3i relativeLocation) {
//        Vector3i delta = new Vector3i((int) (relativeLocation.getX() - map.getOrigin().getBlockX()), (int) (relativeLocation.getY() - map.getOrigin().getBlockY()), (int) (relativeLocation.getZ() - map.getOrigin().getBlockZ()));
//        return new Location(origin.getWorld(), origin.getBlockX() + delta.getX(), origin.getBlockY() + delta.getY(), origin.getBlockZ() + delta.getZ());
//    }

    @Override
    public void placeBanner(GameFlagTeam team, Location location, BlockFace rotation) {
        Block b = location.getBlock();
        b.setType(team.getTeam().getBannerItem().getType());
        Banner banner = (Banner) b.getState();
        banner.setBaseColor(team.getTeam().getBaseColor());
        banner.setPatterns(team.getTeam().getPatterns());
        banner.update();
//            team.getFlagSpawn().getBlock().setBlockData(banner.getBlockData());

        Rotatable r = (Rotatable) banner.getBlockData();
        if (rotation == null) {
            r.setRotation(team.getTeam().getRotation());
        } else {
            r.setRotation(rotation);
        }
        b.setBlockData(r);
        this.flagManager.getBlockFlags().put(location, team);

        Main.getInstance().getEffectManager().addLoopEffect(team.getRingEffect(), location.clone().add(0.5,0,0.5));

//        System.out.println("Placed banner to real location: " + location);
    }
}
