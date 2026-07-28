package me.bananplayss.castlewars.core.utils;

import com.cryptomorin.xseries.XSound;
import lombok.Getter;
import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.api.utils.RespawnManager;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.RingOuterEffect;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.messages.Message;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class RespawnManagerImpl implements RespawnManager {

    private final Map<Player, Long> respawns;

    public RespawnManagerImpl() {
        this.respawns = new ConcurrentHashMap<>();

        checker();
    }

    public boolean isDead(Player player) {
        return this.respawns.containsKey(player);
    }

    public void removeRespawn(Player player) {
        this.respawns.remove(player);
    }

    public void checker() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            long unixTime = System.currentTimeMillis();
            for (Map.Entry<Player, Long> entry : respawns.entrySet()) {
                if (entry.getValue() <= unixTime) {
                    Profile prof = ProfileCacheImpl.getProfileImpl(entry.getKey());
                    if (prof.getCurrentGame() != null && prof.getTeam() != null) {
                        onRespawn(prof, entry.getKey());
                        respawns.remove(entry.getKey());
                        entry.getKey().clearTitle();
                    }
                } else {
                    setDeathTitle(entry.getKey(), (int) Math.ceil((double) (entry.getValue() - System.currentTimeMillis()) / 1000));
                }
            }
        }, 1L, 1L);
    }

    public void addRespawn(Player player) {
        this.respawns.put(player, System.currentTimeMillis() + Main.getInstance().getConfigData().getRespawnTime());
    }

    private void onRespawn(Profile profile, Player player) {
        //player.setGameMode(GameMode.SURVIVAL);
        Kit kit = Main.getInstance().getKitManager().getKit(profile.getCurrentGame().getKitName());
        kit.give(player);

        if(profile.getCurrentGame() instanceof FlagGameImpl fg) {
            RingOuterEffect effect = RingOuterEffect.create(((GameFlagTeam) profile.getTeam()));
            Main.getInstance().getEffectManager().addLoopEffect(effect, profile.getTeam().getSpawn());
        }

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);

        profile.getCurrentGame().getSpectateManager().removeSpectate(player.getUniqueId());
        XSound.BLOCK_NOTE_BLOCK_PLING.play(player, 1, 3);

        player.teleport(profile.getTeam().getSpawn());
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            player.updateInventory();
            player.setVelocity(new Vector(0, 0.01, 0));
        }, 1L);
    }


    private void setDeathTitle(Player player, int seconds) {
        player.showTitle(Title.title(
                Message.RESPAWN_TITLE.builder().setTime(seconds + "").getComponent(player),
                Message.RESPAWN_SUBTITLE.builder().setTime(seconds + "").getComponent(player),
                Title.Times.times(Duration.ZERO, Duration.ofMillis(1000), Duration.ZERO))
        );
    }
}
