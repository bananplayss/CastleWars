package me.bananplayss.castlewars.core.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.flags.GameFlagManager;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import me.bananplayss.castlewars.api.teams.FlagTeam;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.map.ArenaSchematic;
import me.bananplayss.castlewars.core.map.managers.WorldEditMapManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Banner;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
public class FlagGame extends Game {
    public final static NamespacedKey FLAG_TEAM_KEY = new NamespacedKey(Main.getInstance(), "team");

    private final ArenaSchematic map;

    private final GameFlagManager flagManager;

//    private Map<String, GameFlagTeam> gameTeams;
    //AbsLoc = GameArenaCenter + (RelativePos - PrefabOrigin)

    private Location origin;

    public FlagGame(int id, ArenaSchematic map, BaseArena arenaConfig) {
        super(id, arenaConfig);
        this.map = map;

        for (Map.Entry<Location, ArenaSchematic> entry : ((WorldEditMapManager) Main.getInstance().getMapManager().getManager()).getBuiltMaps().entrySet()) {
            if (entry.getValue().equals(this.map)) {
                this.origin = entry.getKey();
                break;
            }
        }

        // load teams
        for (Map.Entry<String, AbstractTeam> baseTeams : arenaConfig.getTeams().entrySet()) {
            GameFlagTeam t = new GameFlagTeam(
                    relLocationToAbsolute(arenaConfig.getTeams().get(baseTeams.getKey()).getSpawn()),
                    BoundingBox.of(
                            relLocationToAbsolute(baseTeams.getValue().getBoundingBox().getBound1()),
                            relLocationToAbsolute(baseTeams.getValue().getBoundingBox().getBound2())
                    ),
                    (FlagTeam) baseTeams.getValue()
            );
            t.setFlagSpawn(relLocationToAbsolute(((FlagTeam) baseTeams.getValue()).getFlagVector()));
            this.teams.put(baseTeams.getKey(), t);
        }

        this.spectatorSpawn = relLocationToAbsolute(arenaConfig.getSpectatorVector());
        this.lobby = relLocationToAbsolute(arenaConfig.getSpectatorVector());

        this.flagManager = new GameFlagManager();
        placeBanners();
    }

    public void placeBanners() {
        for (AbstractGameTeam value : this.teams.values()) {
            GameFlagTeam team = (GameFlagTeam) value;

            team.getFlagSpawn().getBlock().setType(team.getTeam().getBannerItem().getType());
            Banner banner = (Banner) team.getFlagSpawn().getBlock().getState();
            banner.setBaseColor(team.getTeam().getBaseColor());
            banner.setPatterns(team.getTeam().getPatterns());
            banner.update();
//            team.getFlagSpawn().getBlock().setBlockData(banner.getBlockData());

            Rotatable r = (Rotatable) banner.getBlockData();
            r.setRotation(team.getTeam().getRotation());
            team.getFlagSpawn().getBlock().setBlockData(r);
        }
    }

    // mar megirva sima game-be
//    public AbstractGameTeam getRandomTeam() {
//        return teams.values().stream().filter(t -> t.getPlayers().size() < arenaConfig.getTeamSize()).findAny().orElse(null); // Átlátható be like:
//    }

    @Nullable
    public GameFlagTeam getTeam(Player player) {
        for (AbstractGameTeam value : this.teams.values()) {
            if(value.getPlayers().contains(player))
                return (GameFlagTeam) value;
        }
        return null;
    }

    public void broadcast(Component component) {
        for (AbstractGameTeam value : this.teams.values()) {
            for (Player player : value.getPlayers()) {
                player.sendMessage(component);
            }
        }
    }

//    private void dropFlag(Player player) {
//        //TODO: KIT helmet nem null
//        ItemStack banner = player.getInventory().getHelmet().clone();
//        player.getInventory().setHelmet(null);
//        player.setGlowing(false);
//        for (int i = player.getLocation().getBlockY() - 1; i > -100; i--) {
//            Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), i, player.getLocation().getBlockZ());
//            if(block.isSolid()) {
//                block.setType(banner.getType());
//
//                Banner bannerState = (Banner) block.getState();
//                BannerMeta meta = (BannerMeta) banner.getItemMeta();
//
//                bannerState.setPatterns(meta.getPatterns());
//                break;
//            }
//        }
//        broadcast(Message.DROPPED_FLAG.builder().setPlayer(player).getComponent());
//    }


    private Location relLocationToAbsolute(VectorLocation relativeLocation) {
        Vector3i delta = new Vector3i((int) (relativeLocation.getX() - map.getOrigin().getBlockX()), (int) (relativeLocation.getY() - map.getOrigin().getBlockY()), (int) (relativeLocation.getZ() - map.getOrigin().getBlockZ()));
        return new Location(origin.getWorld(),origin.getBlockX() + delta.getX(),origin.getBlockY() + delta.getY(),origin.getBlockZ() + delta.getZ());
    }
    private Location relLocationToAbsolute(Vector3i relativeLocation) {
        Vector3i delta = new Vector3i((int) (relativeLocation.getX() - map.getOrigin().getBlockX()), (int) (relativeLocation.getY() - map.getOrigin().getBlockY()), (int) (relativeLocation.getZ() - map.getOrigin().getBlockZ()));
        return new Location(origin.getWorld(),origin.getBlockX() + delta.getX(),origin.getBlockY() + delta.getY(),origin.getBlockZ() + delta.getZ());
    }
}
