package me.bananplayss.castlewars.core.listeners;

import com.cryptomorin.xseries.XSound;
import me.bananplayss.castlewars.api.events.flags.CastleWarsFlagDropEvent;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.messages.Message;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import me.bananplayss.castlewars.core.utils.LocationUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerDeathListener implements Listener {


    private final static List<XSound> killSounds = List.of(
            XSound.BLOCK_FIRE_EXTINGUISH,
            XSound.ENTITY_GENERIC_EXTINGUISH_FIRE,
            XSound.ENTITY_CAT_DEATH,
            XSound.ENTITY_CREEPER_DEATH,
            XSound.BLOCK_LAVA_EXTINGUISH
    );


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof == null) return;
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

        if (e.getPlayer().getKiller() != null) {
            killSounds.get(ThreadLocalRandom.current().nextInt(killSounds.size())).play(e.getPlayer().getKiller(),0.5f,0.3f);
        }

        if (prof.getCurrentGame() instanceof FlagGame fg) {
            boolean resettedToSpawn = false;
            GameFlagTeam carriedTeam = fg.getFlagManager().getCarriedFlags().get(e.getPlayer());
            if (carriedTeam != null) {
                for (AbstractGameTeam value : prof.getCurrentGame().getTeams().values()) {
                    if (value != carriedTeam) continue;
                    if (LocationUtils.isInside(e.getPlayer(), value.getCorner1(), value.getCorner2())) {
                        CastleWarsFlagDropEvent event = new CastleWarsFlagDropEvent(prof.getCurrentGame(), e.getPlayer(), carriedTeam, carriedTeam.getFlagSpawn());
                        event.callEvent();

                        fg.getFlagManager().resetFlag(e.getPlayer(), carriedTeam);
                        resettedToSpawn = true;
                        break;
                    }
                }
            }

            if (!resettedToSpawn) {
                GameFlagTeam t = fg.getFlagManager().drop(e.getPlayer());
                CastleWarsFlagDropEvent event = new CastleWarsFlagDropEvent(prof.getCurrentGame(), e.getPlayer(), carriedTeam, fg.getFlagManager().getFlagLocation(t));
                event.callEvent();
                if (t != null) {
                    Message.FLAG_DROPPED.builder().setTeam(t).setPlayer(e.getPlayer()).send(prof.getCurrentGame());
                    t.setFlagProtection(fg.getFlagProtection() + System.currentTimeMillis());
                }
            }

            if(fg.isRespawnEnabled()) {
                Main.getInstance().getRespawnManager().addRespawn(e.getPlayer());
            } else {
                e.getPlayer().showTitle(Title.title(
                        Message.DIED_TITLE.builder().getComponent(e.getPlayer()),
                        Message.DIED_SUBTITLE.builder().getComponent(e.getPlayer()),
                        Title.DEFAULT_TIMES
                ));
            }
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            e.getPlayer().spigot().respawn();
            e.getPlayer().setHealth(20);
            e.getPlayer().setFoodLevel(20);
            e.getPlayer().setFireTicks(0);
            e.getPlayer().teleport(prof.getCurrentGame().getSpectatorSpawn());
            prof.getSpectator().setSpectator();
            XSound.BLOCK_NOTE_BLOCK_PLING.play(e.getPlayer(),1,5);

            if(prof.getCurrentGame() instanceof FlagGame fg) {
                if(!fg.isRespawnEnabled()) {
                    if (!prof.getTeam().isTeamAlive()) {
                        prof.getTeam().setDead(true);
                        System.out.println("Team kiesett: " + prof.getTeam().getTeam().getKey());
                    }
                }
            }
        }, 1L);

    }
}
