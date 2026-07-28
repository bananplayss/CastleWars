package me.bananplayss.castlewars.core.digging;

import lombok.Getter;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.RingFillEffect;
import me.bananplayss.castlewars.core.effects.RingOuterEffect;
import me.bananplayss.castlewars.core.utils.TeamColorConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFlagResetManager {

    private final FlagGame game;
    @Getter private final Map<Player, Long> times;

    public GameFlagResetManager(FlagGame game) {
        this.game = game;
        this.times = new HashMap<>();
    }

    public void tick() {
        for (Map.Entry<Player, Long> entry : this.times.entrySet()) {
            long diff = entry.getValue() - System.currentTimeMillis();
            if (diff <= 0) {
                GameFlagTeam t = (GameFlagTeam) this.game.getGame().getTeam(entry.getKey());
                if (t == null) continue;

                Block b = null;
                for (Map.Entry<Location, GameFlagTeam> team : this.game.getFlagManager().getBlockFlags().entrySet()) {
                    if (!t.equals(team.getValue())) continue;
                    b = team.getKey().getBlock();
                    break;
                }

                if (b == null) continue;
//                this.game.getFlagManager().resetFlag(b, t);
                BlockBreakEvent event = new BlockBreakEvent(b, entry.getKey());
                Bukkit.getPluginManager().callEvent(event);

                this.times.remove(entry.getKey());
            }
        }

        for (Map.Entry<Location, GameFlagTeam> banner : this.game.getFlagManager().getBlockFlags().entrySet()) {
            GameFlagTeam bannerTeam = banner.getValue();
            if (bannerTeam.hasFlagProtection()) {
//                System.out.println("Védelem alatt van");
                continue;
            }

            if (bannerTeam.isOnSpawn(banner.getKey())) {
//                System.out.println("Már a spawnon van");
                continue;
            }

            Location center = banner.getKey().clone().add(0.5, 0, 0.5);

            long nearestResetTime = 0;
            List<Player> nearbyPlayers = new ArrayList<>(center.getNearbyPlayers(2));
            for (Player player : banner.getValue().getPlayers()) {
                if (!nearbyPlayers.contains(player) || !player.isSneaking()) {
//                    RingOuterEffect outer = new RingOuterEffect(TeamColorConverter.parseColorOrThrow(bannerTeam.getTeam().getColor()), 500, 2, null);
//                    outer.setLooping(true);
//                    bannerTeam.setCurrentEffect(outer);
//
//                    Main.getInstance().getEffectManager().addLoopEffect(outer, center);
                    this.times.remove(player);
                    continue;
                }

                long resetTime = System.currentTimeMillis() + this.game.getFlagResetTime();
                if (!this.times.containsKey(player)) {
//                    System.out.println("Elkezdett shiftelni");
//                    RingFillEffect fill = new RingFillEffect(TeamColorConverter.parseColorOrThrow(bannerTeam.getTeam().getColor()), 3000, 2, null);
//                    Main.getInstance().getEffectManager().removeLoopEffect(bannerTeam.getCurrentEffect());
//                    bannerTeam.setCurrentEffect(fill);
                    this.times.put(player, resetTime);
                } else {
                    resetTime = this.times.get(player);
                }

                if (nearestResetTime > resetTime || nearestResetTime == 0) {
                    nearestResetTime = resetTime;
                }
            }

            // reset
            if(nearestResetTime == 0) {
                CustomEffect currEff = bannerTeam.getCurrentEffect();
                if(currEff instanceof RingOuterEffect) continue;
                Main.getInstance().getEffectManager().removeLoopEffect(currEff);
                RingOuterEffect outer = RingOuterEffect.create(bannerTeam);
                outer.setLooping(true);
                bannerTeam.setCurrentEffect(outer);
                Main.getInstance().getEffectManager().addLoopEffect(outer, center);
            } else {
                CustomEffect currEff = bannerTeam.getCurrentEffect();
                if(!(currEff instanceof RingFillEffect)) {
                    Main.getInstance().getEffectManager().removeLoopEffect(currEff);
                    currEff = new RingFillEffect(TeamColorConverter.parseColorOrThrow(bannerTeam.getTeam().getColor()), this.game.getFlagResetTime(), 2, null);
                    currEff.setStartTime(nearestResetTime - this.game.getFlagResetTime());
                    bannerTeam.setCurrentEffect(currEff);
                    Main.getInstance().getEffectManager().addLoopEffect(currEff, center);
                }
            }
        }
    }
}
