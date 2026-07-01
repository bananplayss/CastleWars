package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if (prof.getCurrentGame() == null) return;
        if (prof.getTeam() == null) return;

        e.getPlayer().setGlowing(false);
        e.getPlayer().getInventory().clear();
        e.getDrops().clear();
        //e.setDroppedExp(0);
        e.setKeepInventory(false);
        e.setKeepLevel(false);
        e.getPlayer().setExp(0f);
        e.deathMessage(ColorParser.parse("Meghalt " + e.getPlayer().getName()));

        if(prof.getCurrentGame() instanceof FlagGame fg) {
            boolean resettedToSpawn = false;
            GameFlagTeam carriedTeam = fg.getFlagManager().getCarriedFlags().get(e.getPlayer());
            if(carriedTeam != null) {
                for (AbstractGameTeam value : prof.getCurrentGame().getTeams().values()) {
                    if(value != carriedTeam) continue;
                    if (LocationUtils.isInside(e.getPlayer().getLocation(), value.getBoundingBox())) {
                        fg.getFlagManager().resetFlag(e.getPlayer(), carriedTeam);
                        resettedToSpawn = true;
                        break;
                    }
                }
            }

            if(!resettedToSpawn) {
                GameFlagTeam t = fg.getFlagManager().drop(e.getPlayer());
                if(t != null) {
                    t.setFlagProtection(fg.getFlagProtection());
                }
            }
        }

        Main.getInstance().getRespawnManager().addRespawn(e.getPlayer());

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            e.getPlayer().spigot().respawn();
            e.getPlayer().setHealth(20);
            e.getPlayer().setFoodLevel(20);
            e.getPlayer().setFireTicks(0);
            e.getPlayer().teleport(prof.getCurrentGame().getSpectatorSpawn());
//            e.getPlayer().setGameMode(GameMode.SPECTATOR);
        }, 1L);
    }
}
