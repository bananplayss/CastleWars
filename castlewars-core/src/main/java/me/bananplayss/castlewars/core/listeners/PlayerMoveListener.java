package me.bananplayss.castlewars.core.listeners;

import com.cryptomorin.xseries.XSound;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.BleedEffect;
import me.bananplayss.castlewars.core.effects.BoundingBoxVolumeEffect;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.messages.Message;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.utils.LocationUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ() && e.getFrom().getBlockY() == e.getTo().getBlockY()) return;
        //BleedEffect a = new BleedEffect(e.getPlayer().getLocation(), 1000);
        //a.apply();
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof == null) return;
        if(prof.getCurrentGame() == null) return;

        if(prof.getCurrentGame() instanceof FlagGameImpl fg) {
            if (fg.getFlagManager().getCarriedFlags().containsKey(e.getPlayer())) {
                GameFlagTeam ft = fg.getTeam(e.getPlayer());

                if(LocationUtils.isInsideBlockOnly(e.getPlayer(), ft.getCorner1(), ft.getCorner2())) {
                    GameFlagTeam bevittBannerTeam = fg.getFlagManager().getCarriedFlags().get(e.getPlayer());
                    fg.getFlagManager().resetFlag(e.getPlayer(), bevittBannerTeam);

                    ft.addScore();

                    e.getPlayer().setGlowing(false);
                    fg.getKit().giveHelmet(e.getPlayer());

                    Message.FLAG_SCORED.builder().setTeam(bevittBannerTeam).setCurrentScore(ft.getScore()).setMaxScore(fg.getBaseArena().getScoreLimit()).setPlayer(e.getPlayer()).send(prof.getCurrentGame());

                    System.out.println("Bevitte " + e.getPlayer().getName() + " team: " + bevittBannerTeam.getTeam().getKey() + " bannerjét");
                    BoundingBox b = BoundingBox.of(e.getPlayer().getLocation(), e.getPlayer().getLocation());
                    b.expand(2,2,2);
                    BoundingBoxVolumeEffect effect1 = new BoundingBoxVolumeEffect(Particle.FIREWORKS_SPARK,0,b,50);
                    BoundingBoxVolumeEffect effect2 = new BoundingBoxVolumeEffect(Particle.FLAME,0,b,50);
                    Main.getInstance().getEffectManager().oneShotEffect(effect1, b.getCenter().toLocation(e.getPlayer().getWorld()));
                    Main.getInstance().getEffectManager().oneShotEffect(effect2, b.getCenter().toLocation(e.getPlayer().getWorld()));

                    for (Player allPlayer : fg.getAllPlayers()) {
                        XSound.ENTITY_ENDER_DRAGON_GROWL.play(allPlayer);
                    }

                    if(ft.getScore() >= fg.getBaseArena().getScoreLimit()) {
                        fg.end(ft);
                        return;
                    }

                    e.getPlayer().getWorld().strikeLightningEffect(bevittBannerTeam.getFlagSpawn());
                }
            }
        }
    }
}
